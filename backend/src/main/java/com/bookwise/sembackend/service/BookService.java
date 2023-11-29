package com.bookwise.sembackend.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson2.JSON;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.model.api.SearchReq;
import com.bookwise.sembackend.web_crawler.YLibEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class BookService {
    public enum SearchType {
        AMBIGUOUS,
        SPECIFIC
    }

    @Value("${api.ylib.url}")
    private String ylibUrl;



    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ElasticsearchClient client;

    public List<ESBook> recommendBooks(int count, String category) {
        try {
            SearchResponse<ESBook> response = _search(category, "category");
            List<Hit<ESBook>> hits = response.hits().hits();
            log.info("Recommend book hit count: " + hits.size());
            List<ESBook> esBooks = new ArrayList<>(hits.stream().map(Hit::source).toList());
            Collections.shuffle(esBooks);
            return esBooks.subList(0, Math.min(count, esBooks.size()));

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Hit<ESBook>> proSearch(SearchReq body, int size) {
        // isFuzzy:［false,false,false, false,false,false］，//是否模糊查询，0-5分别对应 书籍名称、出版社、来源
        try {
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index("books")
                    .size(size)
                    .query(q -> {
                        q.bool(b -> {
                            if (!body.getBookLanguage().isEmpty()) {
                                b.must(mu -> mu.match(m -> m.field("bookLanguage").query(body.getBookLanguage())));
                            }
                            if (!body.getName().isEmpty()) {
                                if (body.getIsFuzzy().get(0)) {
                                    b.must(mu -> mu.fuzzy(m -> m.field("name").value(body.getName()).fuzziness("AUTO")));
                                } else {
                                    b.must(mu -> mu.match(m -> m.field("name").query(body.getName())));
                                }
                            }
                            if (!body.getPublisher().isEmpty()) {
                                if (body.getIsFuzzy().get(1)) {
                                    b.must(mu -> mu.fuzzy(m -> m.field("publisher").value(body.getPublisher()).fuzziness("AUTO")));
                                } else {
                                    b.must(mu -> mu.match(m -> m.field("publisher").query(body.getPublisher())));
                                }
                            }
                            if (!body.getCategory().isEmpty()) {
                                b.must(mu -> mu.bool(bb -> {
                                    for (String c : body.getCategory()) {
                                        bb.should(sh -> sh.match(m -> m.field("category").query(c)));
                                    }
                                    return bb;
                                }));
                            }
                            if (!body.getSource().isEmpty()) {
                                if (body.getIsFuzzy().get(2)) {
                                    b.must(mu -> mu.fuzzy(m -> m.field("source").value(body.getSource()).fuzziness("AUTO")));
                                } else {
                                    b.must(mu -> mu.match(m -> m.field("source").query(body.getSource())));
                                }
                            }
                            if (body.getPages() != 0) {
                                b.must(mu -> mu.range(r -> r
                                        .field("pages")
                                        .from(String.valueOf(body.getPages() - 10))
                                        .to(String.valueOf(body.getPages() + 10))
                                ));
                            }
                            if (!body.getIsbn().isEmpty()) {
                                b.must(mu -> mu.fuzzy(r -> r
                                        .field("isbn")
                                        .value(body.getIsbn())
                                        .fuzziness("AUTO")
                                ));
                            }
                            if (body.getPublishYear().size() == 2) {
                                b.must(mu -> mu.range(r -> r
                                        .field("publishYear")
                                        .from(String.valueOf(body.getPublishYear().get(0)))
                                        .to(String.valueOf(body.getPublishYear().get(1)))
                                ));
                            }
                            return b;
                        });
                        return q;
                    }).build();

            SearchResponse<ESBook> search = client.search(searchRequest, ESBook.class);
            log.info("Hit count: " + search.hits().hits().size());
            return search.hits().hits();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        System.out.println(body);
        return null;
    }

    @Deprecated
    public List<Hit<ESBook>> search(String query, String type) {

        try {
            if (type == null) {
                SearchResponse<ESBook> search = client.search(s -> s
                                .index("books")
                                .query(qq -> qq
                                        .multiMatch(t -> t
                                                // 我想要如果type==null，就做模糊搜索，不只是查找name，是所有的属性
                                                .fields("name", "category", "publisher", "isbn")
                                                .query(query)
                                                .type(TextQueryType.BestFields)
                                        )
                                ),
                        ESBook.class
                );

                return search.hits().hits();
            } else {
                String _type = "";
                switch (type) {
                    case "NAME":
                        _type = "name";
                        break;
                    case "PUBLISHER":
                        _type = "publisher";
                        break;
                    case "CATEGORY":
                        _type = "category";
                        break;
                    case "BOOK_LANGUAGE":
                        _type = "bookLanguage";
                        break;
                    case "PUBLISH_YEAR":
                        _type = "publishYear";
                        break;
                    case "ISBN":
                        _type = "isbn";
                        break;
                }
                SearchResponse<ESBook> search = _search(query, _type);
                return search.hits().hits();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getBookCategories() {
        return getDistinctValues("category", "books");
    }

    public List<String> getBookLanguages() {
        return getDistinctValues("bookLanguage", "books");
    }
    private List<String> getDistinctValues(String field, String index) {
        try {
            SearchResponse<ESBook> response = client.search(q -> q
                            .index(index)
                            .size(0)
                            .from(0)
                            .aggregations(field, a -> a
                                    .terms(t -> t
                                            .field(field + ".keyword")))

                    , ESBook.class);
            StringTermsAggregate terms = response.aggregations().get(field).sterms();

            List<String> categories = terms.buckets()
                    .array()
                    .stream()
                    .map(StringTermsBucket::key)
                    .toList()
                    .stream()
                    .map(FieldValue::_toJsonString)
                    .toList();

            for (String i : categories) {
                System.out.println(i);
            }

            return categories;

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void addBook(ESBook book) {
        try {
            System.out.println(book);
            IndexRequest<Object> indexRequest = new IndexRequest.Builder<>()
                    .index("books")
                    .id(book.getUuid())
                    .document(book)
                    .build();
            client.index(indexRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private List<ESBook> searchYLib() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        /**
         * {
         *     "keyword": "harry potter",
         *     "page": 1,
         *     "sensitive": false
         * }
         */
        Map<String, Object> body = new HashMap<>();
        body.put("keyword", "harry potter");
        body.put("page", 1);
        body.put("sensitive", false);

        String jsonData = JSON.toJSONString(body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ylibUrl, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            YLibEntity entity = objectMapper.readValue(responseEntity.getBody(), YLibEntity.class);

            for (YLibEntity.Book book : entity.getData()) {
                System.out.println(book);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    private SearchResponse<ESBook> _search(String query, String field) throws IOException {
        SearchResponse<ESBook> search = client.search(s -> s
                        .index("books")
                        .query(qq -> qq
                                .match(t -> t
                                        .field(field)
                                        .query(query)
                                )
                        ),
                ESBook.class
        );
        return search;
    }
}
