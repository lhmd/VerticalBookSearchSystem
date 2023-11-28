package com.bookwise.sembackend.web_crawler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YLibEntity {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Book {
        String title;
        String author;
        String publisher;
        String isbn;
        String extension;
        String filesize;
        String year;
        String id;
        String source;
    }
    List<Book> data;
    Integer hits;
}
