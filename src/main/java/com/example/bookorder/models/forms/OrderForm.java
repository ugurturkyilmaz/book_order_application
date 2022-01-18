package com.example.bookorder.models.forms;

import com.example.bookorder.models.enums.OrderStatus;
import com.example.bookorder.utils.ErrorMessages;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class OrderForm extends BaseForm {
    private Date orderDate;
    private OrderStatus status;
    private CustomerForm customer;
    @NotNull(message = ErrorMessages.BOOKS_CANNOT_BE_NULL)
    @Valid
    private List<OrderedBookForm> books;
    @NotNull(message = ErrorMessages.CUSTOMER_ID_CANNOT_BE_NULL)
    private String customerId;
    private double totalPrice;
}
