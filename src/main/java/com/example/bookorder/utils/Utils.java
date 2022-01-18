package com.example.bookorder.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static ValidationResult getBindingResult(BindingResult bindingResult) {
        ValidationResult validationResult = new ValidationResult();
        List<String> errorMessages = new ArrayList<>();
        if (bindingResult.getAllErrors() != null && bindingResult.getAllErrors().size() > 0) {
            for (Object object : bindingResult.getAllErrors()) {

                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    errorMessages.add(fieldError.getDefaultMessage());
                } else if (object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    errorMessages.add(objectError.getDefaultMessage());
                }
            }
        }
        validationResult.setSuccessful(false);
        validationResult.setMessages(errorMessages);

        return validationResult;
    }

    public static Date stringToDate(String date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

}
