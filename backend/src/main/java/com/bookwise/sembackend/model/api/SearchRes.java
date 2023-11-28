package com.bookwise.sembackend.model.api;

import com.bookwise.sembackend.elastic_search.ESBook;

import java.util.List;

public class SearchRes extends ResultBox {
    public List<ESBook> books;

    public SearchRes(boolean success, String message, List<ESBook> books) {
        super(success, message);
        this.books = books;
    }
}
