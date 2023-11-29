package com.bookwise.sembackend.controller;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.model.api.*;
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

    @Deprecated
    @PostMapping("/deprecated-search")
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

    @PostMapping("/search")
    public SearchRes search(@RequestBody SearchReq body) {
        List<Hit<ESBook>> hitBooks = bookService.proSearch(body, 100);

        ArrayList<ESBook> books = new ArrayList<>();
        for (Hit<ESBook> hit : hitBooks) {
//            System.out.println(hit);
            books.add(hit.source());
        }
        return new SearchRes(false, "Not yet implement", books);
    }

    @PostMapping("/add-book")
    public void addBook(@RequestBody ESBook book) {
        bookService.addBook(book);
    }

    @PostMapping("/interest")
    public RecommendBooks interest(@RequestBody String category) {
        List<ESBook> books = bookService.recommendBooks(5, category);
        if (books != null) {
            return new RecommendBooks(true, "", books);
        }
        return new RecommendBooks(false, "Something wrong", null);
    }

    @PostMapping("/category")
    public BookCategory bookCategory() {
        List<String> categories = bookService.getBookCategories();
        return new BookCategory(true, "Available book categories in database", categories);
    }

    @PostMapping("/bookLanguages")
    public BookLanguages bookLanguages() {
        List<String> bookLanguages = bookService.getBookLanguages();
        return new BookLanguages(true, "Available book languages in database", bookLanguages);
    }
}
