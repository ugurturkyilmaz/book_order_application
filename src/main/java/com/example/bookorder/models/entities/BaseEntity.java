package com.example.bookorder.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity {

    @Id
    private String id;
}
