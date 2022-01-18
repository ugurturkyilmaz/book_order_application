package com.example.bookorder.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Book extends BaseEntity {

    private String name;
    private Integer stock;
    private double price;
}
