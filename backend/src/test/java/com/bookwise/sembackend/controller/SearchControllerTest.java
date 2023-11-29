package com.bookwise.sembackend.controller;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.bookwise.sembackend.elastic_search.ESBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class SearchControllerTest {
    @Autowired
    private SearchController searchController;

    @Test
    public void test() {
        for (int i = 0; i < 1000; i++) {
            searchController.addBook(ESBook.generateRandomBook());
        }
    }

    @Test
    public void testSearch() {
        List<ESBook> books = searchController.search("Harry", "");
        for (ESBook b : books) {
            System.out.println(b);
        }
    }
}
