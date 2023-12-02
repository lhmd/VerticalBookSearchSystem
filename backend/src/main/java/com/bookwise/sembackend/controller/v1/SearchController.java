package com.bookwise.sembackend.controller.v1;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.model.api.*;
import com.bookwise.sembackend.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class SearchController {
    @Autowired
    private BookService bookService;

    @Value("${api.messages.error}")
    private String messagesError;

    @Deprecated
    @PostMapping("/deprecated-search")
    public List<ESBook> deprecatedSearch(
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
        try {
            System.out.println(body);
            List<Hit<ESBook>> hitBooks = bookService.proSearch(body, 100);

            ArrayList<ESBook> books = new ArrayList<>();
            for (Hit<ESBook> hit : hitBooks) {
                books.add(hit.source());
            }
            return new SearchRes(true, "Search result", books);
        } catch (Exception e) {
            return new SearchRes(false, messagesError, null);
        }

    }

    @PostMapping("/add-book")
    public ResultBox addBook(@RequestBody ESBook book) {
        try {
            bookService.addBook(book);
            return new ResultBox(true, "Successfully added book");
        } catch (Exception e) {
            log.error("Message: " + e.getMessage());
            return new ResultBox(false, messagesError);
        }
    }

    @PostMapping("/interest")
    public RecommendBooks interest(@RequestBody Interest.InterestParams params) {
        try {
            List<ESBook> books = bookService.recommendBooks(5, params.category);
            return new RecommendBooks(true, "Recommended books for user", books);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new RecommendBooks(false, messagesError, null);
        }
    }

    @PostMapping("/category")
    public BookCategory bookCategory() {
        try {
            List<String> categories = bookService.getBookCategories();
            return new BookCategory(true, "Available book categories in database", categories);
        } catch (Exception e) {
            log.error("Message: " + e.getMessage());
            return new BookCategory(false, messagesError, null);
        }

    }

    @PostMapping("/bookPublishers")
    public BookPublishers bookPublishers() {
        try {
            List<String> publishers = bookService.getBookPublishers();
            return new BookPublishers(true, "Available book publishers in database", publishers);
        } catch (Exception e) {
            log.error("Message: " + e.getMessage());
            return new BookPublishers(false, messagesError, null);
        }
    }

    @PostMapping("/bookLanguages")
    public BookLanguages bookLanguages() {
        try {
            List<String> bookLanguages = bookService.getBookLanguages();
            return new BookLanguages(true, "Available book languages in database", bookLanguages);
        } catch (Exception e) {
            log.error("Message: " + e.getMessage());
            return new BookLanguages(false, messagesError, null);
        }
    }
}
