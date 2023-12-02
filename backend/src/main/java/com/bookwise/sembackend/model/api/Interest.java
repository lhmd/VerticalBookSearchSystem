package com.bookwise.sembackend.model.api;

public class Interest extends ResultBox{
    public static class InterestParams {
        public String category;

        @Override
        public String toString() {
            return "InterestParams{" +
                    "category='" + category + '\'' +
                    '}';
        }
    }
    public Interest(boolean success, String message) {
        super(success, message);
    }
}
