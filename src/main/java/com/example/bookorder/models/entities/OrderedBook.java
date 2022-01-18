package com.example.bookorder.models.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class OrderedBook {
    @DBRef
    private Book book;
    private Integer quantity;
    private double price;
}
