package com.bookwise.sembackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    private String name;
    private String category;
    private String publisher;
    private Integer pages;
    private Integer publishYear;
    private String bookLanguage;
    private String ISBN;
    private String imageUrl;
}
