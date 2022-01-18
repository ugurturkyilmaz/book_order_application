package com.example.bookorder.services.impl;

import com.example.bookorder.models.EntityFormMapper;
import com.example.bookorder.models.entities.Customer;
import com.example.bookorder.models.exceptions.CustomerExistsException;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.forms.CustomerForm;
import com.example.bookorder.repositories.CustomerRepository;
import com.example.bookorder.services.CustomerService;
import com.example.bookorder.utils.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerForm register(CustomerForm customerForm) throws CustomerExistsException {

        //Email or phone check
        Customer customerCheck = customerRepository.findCustomerByEmailOrPhoneNumber(customerForm.getEmail(), customerForm.getPhoneNumber());
        if(customerCheck != null) {
            throw new CustomerExistsException(ErrorMessages.CUSTOMER_ALREADY_EXISTS);
        }

        Customer customer = EntityFormMapper.toCustomer(customerForm);

        customer = customerRepository.insert(customer);

        return EntityFormMapper.toCustomerForm(customer);
    }

    @Override
    public Customer findById(String id) throws EntityNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(!optionalCustomer.isPresent()) {
            throw new EntityNotFoundException(ErrorMessages.CUSTOMER_NOT_FOUND);
        }
        return optionalCustomer.get();
    }
}
