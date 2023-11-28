package com.bookwise.sembackend.model.api;

import java.util.List;

public class BookCategory extends ResultBox{
    public List<String> categories;

    public BookCategory(boolean success, String message, List<String> categories) {
        super(success, message);
        this.categories = categories;
    }
}
