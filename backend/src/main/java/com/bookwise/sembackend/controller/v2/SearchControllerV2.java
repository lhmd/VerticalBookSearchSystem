package com.bookwise.sembackend.controller.v2;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.bookwise.sembackend.dev.ExceptionEnum;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.model.api.*;
import com.bookwise.sembackend.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v2/api")
@Slf4j
public class SearchControllerV2 {
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
    public ResultBox search(@RequestBody SearchReq body) throws IOException {
        List<Hit<ESBook>> hitBooks = bookService.proSearch(body, 100);

        ArrayList<ESBook> books = new ArrayList<>();
        for (Hit<ESBook> hit : hitBooks) {
            books.add(hit.source());
        }
        return ResultBox.success(books);

    }

    @PostMapping("/add-book")
    public ResultBox addBook(@RequestBody ESBook book) {
        try {
            bookService.addBook(book);
            return ResultBox.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/interest")
    public ResultBox interest(@RequestBody Interest.InterestParams params) {
        try {
            List<ESBook> books = bookService.recommendBooks(5, params.category);
            return ResultBox.success(books);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/category")
    public ResultBox bookCategory() {
        try {
            List<String> categories = bookService.getBookCategories();
            return ResultBox.success(categories);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/bookPublishers")
    public ResultBox bookPublishers() {
        try {
            List<String> publishers = bookService.getBookPublishers();
            return ResultBox.success(publishers);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bookLanguages")
    public ResultBox bookLanguages() {
        try {
            List<String> bookLanguages = bookService.getBookLanguages();
            return ResultBox.success(bookLanguages);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
