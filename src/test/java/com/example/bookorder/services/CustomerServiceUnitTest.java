package com.example.bookorder.services;


import com.example.bookorder.models.entities.Customer;
import com.example.bookorder.models.exceptions.CustomerExistsException;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.forms.CustomerForm;
import com.example.bookorder.repositories.CustomerRepository;
import com.example.bookorder.services.impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceUnitTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testRegister() {

        when(customerRepository.findCustomerByEmailOrPhoneNumber(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        when(customerRepository.insert(getCustomer(false))).thenReturn(getCustomer(true));

        try {
            CustomerForm customerForm = customerService.register(getCustomerForm(false));
            Assert.assertNotNull(customerForm);
            Assert.assertEquals("1", customerForm.getId());
        } catch (CustomerExistsException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = CustomerExistsException.class)
    public void testRegisterSameEmailOrPhone() throws CustomerExistsException {

        when(customerRepository.findCustomerByEmailOrPhoneNumber(Mockito.anyString(), Mockito.anyString())).thenReturn(getCustomer(true));

        when(customerRepository.insert(getCustomer(false))).thenReturn(getCustomer(true));

        customerService.register(getCustomerForm(false));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testMissingDataExceptionGetBook() throws EntityNotFoundException {

        Optional<Customer> optionalBook = Optional.empty();
        when(customerRepository.findById(Mockito.anyString())).thenReturn(optionalBook);

        customerService.findById("100");
    }

    private Customer getCustomer(boolean isWithId) {
        Customer customer = new Customer();
        customer.setPhoneNumber("55555555");
        customer.setEmail("ugur.t1993@hotmail.com");
        customer.setFirstName("Uğur");
        customer.setLastName("Türkyılmaz");
        if(isWithId) {
            customer.setId("1");
        }
        return customer;
    }

    private CustomerForm getCustomerForm(boolean isWithId) {
        CustomerForm customer = new CustomerForm();
        customer.setPhoneNumber("55555555");
        customer.setEmail("ugur.t1993@hotmail.com");
        customer.setFirstName("Uğur");
        customer.setLastName("Türkyılmaz");
        if(isWithId) {
            customer.setId("1");
        }
        return customer;
    }
}
