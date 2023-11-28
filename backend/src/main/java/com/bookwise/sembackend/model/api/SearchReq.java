package com.bookwise.sembackend.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchReq {
    private String name;
    private List<String> category;
    private String publisher;
    private Integer pages;
    private List<Integer> publishYear;
    private String bookLanguage;
    private String isbn;
    private String imageUrl;
    private String source;
    private List<Boolean> isFuzzy;
}
