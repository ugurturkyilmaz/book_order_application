package com.example.bookorder.models.forms;

import com.example.bookorder.utils.ErrorMessages;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CustomerForm extends BaseForm {
    @NotNull(message = ErrorMessages.CUSTOMER_FIRSTNAME_CANNOT_BE_NULL)
    private String firstName;
    @NotNull(message = ErrorMessages.CUSTOMER_LASTNAME_CANNOT_BE_NULL)
    private String lastName;
    @NotNull(message = ErrorMessages.CUSTOMER_PHONENUMBER_CANNOT_BE_NULL)
    private String phoneNumber;
    @NotNull(message = ErrorMessages.CUSTOMER_EMAIL_CANNOT_BE_NULL)
    private String email;

}
