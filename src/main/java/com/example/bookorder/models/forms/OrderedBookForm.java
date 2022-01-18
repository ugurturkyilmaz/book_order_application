package com.example.bookorder.models.forms;

import com.example.bookorder.utils.ErrorMessages;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderedBookForm {
    private BookForm book;
    @NotNull(message = ErrorMessages.QUANTITY_CANNOT_BE_NULL)
    @Min(value = 1, message = ErrorMessages.QUANTITY_MUST_BE_GREATER_THAN_ZERO)
    private Integer quantity;
    @NotNull(message = ErrorMessages.BOOK_CANNOT_BE_NULL)
    private String bookId;
    private double price;
}
