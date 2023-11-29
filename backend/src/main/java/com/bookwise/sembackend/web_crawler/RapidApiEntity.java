package com.bookwise.sembackend.web_crawler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapidApiEntity {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Book {
        String title;
        String author;
        String publisher;
        String ISBN;
        String description;
        String img_link;
        String year;
        String pdf_link;
    }

    List<Book> results;
}
