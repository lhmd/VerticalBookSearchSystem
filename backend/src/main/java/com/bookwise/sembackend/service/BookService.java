package com.bookwise.sembackend.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.model.api.SearchReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ElasticsearchClient client;

    /**
     * {
     * "name": "",
     * "bookLanguage": "",
     * "publisher": "",
     * "pages": 0,
     * "isbn": "",
     * "publishYear": [],
     * "source": "",
     * "category": [],
     * "isFuzzy": [
     * false,
     * false,
     * false
     * ]
     * }
     */
    public List<ESBook> randomBooks(int count) throws IOException {
        SearchReq body = new SearchReq(
                "",
                List.of(),
                "",
                0,
                List.of(),
                "",
                "",
                "",
                "",
                List.of(false, false, false)
        );
        List<Hit<ESBook>> hits = proSearch(body, 1000);


        int returnSize = Math.min(count, hits.size());
        int sIdx = ThreadLocalRandom.current().nextInt(0, hits.size() - returnSize);
        int eIdx = sIdx + returnSize - 1;

        log.info(String.format("returnSize: %d, sIdx: %d, eIdx: %d", returnSize, sIdx, eIdx));

        List<ESBook> books = new ArrayList<>(hits.stream().map(Hit::source).toList());

        Collections.shuffle(books);

        return books.subList(sIdx, eIdx);
    }

    public List<ESBook> recommendBooks(int count, String category) throws Exception {
        if (category.isEmpty()) {
            return randomBooks(count);
        } else {
            SearchResponse<ESBook> response = _search(category, "category", count);
            List<Hit<ESBook>> hits = response.hits().hits();
            log.info("Recommend book hit count: " + hits.size());
            List<ESBook> esBooks = new ArrayList<>(hits.stream().map(Hit::source).toList());
            Collections.shuffle(esBooks);
            return esBooks;
        }
    }

    public List<Hit<ESBook>> proSearch(SearchReq body, int size) throws IOException {
        // isFuzzy:［false, false,false］，// 是否模糊查询，0-2分别对应 book name、publisher、source
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
                SearchResponse<ESBook> search = _search(query, _type, 100);
                return search.hits().hits();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getBookCategories() throws IOException {
        List<String> categories = getDistinctValues("category", "books", 200);
        return categories.stream().filter(i -> !(i.contains("|"))).collect(Collectors.toList());
    }

    public List<String> getBookPublishers() throws IOException {
        return getDistinctValues("publisher", "books", 100);
    }

    public List<String> getBookLanguages() throws IOException {
        return getDistinctValues("bookLanguage", "books", 100);
    }

    private List<String> getDistinctValues(String field, String index, int size) throws IOException {
        SearchResponse<ESBook> response = client.search(q -> q
                        .index(index)
                        .size(0)
                        .from(0)
                        .aggregations(field, a -> a
                                .terms(t -> t
                                        .field(field + ".keyword")
                                        .size(size)
                                ))

                , ESBook.class);
        StringTermsAggregate terms = response.aggregations().get(field).sterms();

        List<String> items = terms.buckets()
                .array()
                .stream()
                .map(StringTermsBucket::key)
                .toList()
                .stream()
                .map(FieldValue::_toJsonString)
                .toList();

        log.info("Hits: " + items.size());

        return items;
    }

    public void addBook(ESBook book) throws IOException {
//        System.out.println(book);
        IndexRequest<Object> indexRequest = new IndexRequest.Builder<>()
                .index("books")
                .id(book.getUuid())
                .document(book)
                .build();
        client.index(indexRequest);
    }

    @Deprecated
    private SearchResponse<ESBook> _search(String query, String field, int size) throws IOException {
        SearchResponse<ESBook> search = client.search(s -> s
                        .index("books")
                        .query(qq -> qq
                                .match(t -> t
                                        .field(field)
                                        .query(query)
                                )
                        )
                        .size(size),
                ESBook.class
        );
        return search;
    }
}
