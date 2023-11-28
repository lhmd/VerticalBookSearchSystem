package com.bookwise.sembackend.model.api;

import com.bookwise.sembackend.elastic_search.ESBook;

import java.util.List;

public class RecommendBooks extends ResultBox{
    public RecommendBooks(boolean success, String message, List<ESBook> books) {
        super(success, message);
        this.books = books;
    }

    public List<ESBook> books;
}
