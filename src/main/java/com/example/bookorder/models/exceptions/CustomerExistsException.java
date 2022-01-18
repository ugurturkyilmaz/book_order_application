package com.example.bookorder.models.exceptions;

public class CustomerExistsException extends Exception {

    public CustomerExistsException(String message) {
        super(message);
    }
}
