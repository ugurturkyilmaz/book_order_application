package com.example.bookorder.models.entities;

import com.example.bookorder.models.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("orders")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Order extends BaseEntity {

    private OrderStatus status;
    private Date orderDate;
    @DBRef
    private Customer customer;
    private List<OrderedBook> books;
    private double totalPrice;
}
