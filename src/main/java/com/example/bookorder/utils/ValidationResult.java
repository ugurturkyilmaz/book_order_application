package com.example.bookorder.utils;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<String> messages = new ArrayList<>();
    private boolean isSuccessful = false;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
