package com.bookwise.sembackend.model.api;

import java.util.List;

public class BookLanguages extends ResultBox{
    public List<String> bookLanguages;

    public BookLanguages(boolean success, String message, List<String> bookLanguages) {
        super(success, message);
        this.bookLanguages = bookLanguages;
    }
}
