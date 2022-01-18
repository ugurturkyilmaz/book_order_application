package com.example.bookorder.models.forms;

import com.example.bookorder.utils.ErrorMessages;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class BookForm extends BaseForm {

    @NotNull(message = ErrorMessages.BOOK_NAME_CANNOT_BE_NULL)
    private String name;
    @NotNull(message = ErrorMessages.BOOK_QUANTITY_CANNOT_BE_NULL)
    private Integer quantity;

    @NotNull(message = ErrorMessages.BOOK_PRICE_CANNOT_BE_NULL)
    private Double price;
}
