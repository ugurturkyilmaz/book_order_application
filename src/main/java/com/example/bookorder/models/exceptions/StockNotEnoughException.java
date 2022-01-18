package com.example.bookorder.models.exceptions;

public class StockNotEnoughException extends Exception {

    public StockNotEnoughException(String message) {
        super(message);
    }
}
