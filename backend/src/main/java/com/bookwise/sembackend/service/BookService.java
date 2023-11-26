package com.bookwise.sembackend.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.alibaba.fastjson2.JSON;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.model.YLibEntity;
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

    @Value("${api.rapidapi.url}")
    private String rapidapiUrl;

    @Value("${api.rapidapi.apikey}")
    private String rapidapiApiKey;

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

        }catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

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
