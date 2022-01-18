package com.example.bookorder.controllers;

import com.example.bookorder.models.exceptions.CustomerExistsException;
import com.example.bookorder.models.forms.CustomerForm;
import com.example.bookorder.models.forms.OrderForm;
import com.example.bookorder.services.CustomerService;
import com.example.bookorder.services.OrderService;
import com.example.bookorder.utils.ResultObject;
import com.example.bookorder.utils.Utils;
import com.example.bookorder.utils.ValidationResult;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/${version.name}/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping
    @ApiOperation(value = "Create customer service", notes = "You can create customer")
    public ResponseEntity insert(@Valid @RequestBody CustomerForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationResult validationResult = Utils.getBindingResult(bindingResult);
            return new ResponseEntity(ResultObject.failure(validationResult.getMessages()), HttpStatus.BAD_REQUEST);
        }

        try {
            CustomerForm customerForm = customerService.register(form);
            return new ResponseEntity(ResultObject.success(customerForm), HttpStatus.CREATED);
        } catch (CustomerExistsException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    @ApiOperation(value = "Get orders of the customer", notes = "You can get orders of the customer")
    public ResponseEntity getOrdersOfCustomer(@RequestParam("customerId") String customerId) {

        List<OrderForm> orders = orderService.findByCustomerId(customerId);
        return new ResponseEntity(ResultObject.success(orders), HttpStatus.OK);
    }
}
