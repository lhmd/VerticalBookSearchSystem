package com.bookwise.sembackend.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class SearchController {
    @Autowired
    private BookService bookService;

    @PostMapping("/search")
    public List<ESBook> search(
            @RequestParam(required = false, value = "q") String query,
            @RequestParam(required = false, value = "type") String type
    ) {
        log.info("q: " + query);
        log.info("type: " + type);

        List<Hit<ESBook>> hitBooks = bookService.search(query, type);
        ArrayList<ESBook> books = new ArrayList<>();
        for (Hit<ESBook> hit : hitBooks) {
            System.out.println(hit);
            books.add(hit.source());
        }
        return books;
    }

    @PostMapping("/add-book")
    public void addBook(@RequestBody ESBook book) {
        bookService.addBook(book);
    }
}
