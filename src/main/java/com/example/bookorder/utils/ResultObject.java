package com.example.bookorder.utils;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor(staticName = "of")
public class ResultObject<T> {
    private boolean isSuccessful;
    private List<String> messages;
    private T data;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResultObject<T> failure(List<String> messages) {
        return of(false, messages, null);
    }

    public static <T> ResultObject<T> failure(String message) {
        return of(false, Collections.singletonList(message), null);
    }

    public static <T> ResultObject<T> success(T data) {
        return of(true, null, data);
    }
}
