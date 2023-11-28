package com.bookwise.sembackend.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ResultBox {
    private boolean success;
    private String message;
}
