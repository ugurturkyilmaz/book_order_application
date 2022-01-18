package com.example.bookorder.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("customers")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
