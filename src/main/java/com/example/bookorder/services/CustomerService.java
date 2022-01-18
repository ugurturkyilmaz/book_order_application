package com.example.bookorder.services;

import com.example.bookorder.models.entities.Customer;
import com.example.bookorder.models.exceptions.CustomerExistsException;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.forms.CustomerForm;

public interface CustomerService {

    CustomerForm register(CustomerForm customerForm) throws CustomerExistsException;

    Customer findById(String id) throws EntityNotFoundException;
}
