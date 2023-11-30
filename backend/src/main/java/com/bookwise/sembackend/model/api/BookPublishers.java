package com.bookwise.sembackend.model.api;

import java.util.List;

public class BookPublishers extends ResultBox{
    public List<String> publishers;

    public BookPublishers(boolean success, String message, List<String> publishers) {
        super(success, message);
        this.publishers = publishers;
    }
}
