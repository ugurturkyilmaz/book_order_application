package com.example.bookorder.controllers;

import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.WrongDateFormatException;
import com.example.bookorder.models.forms.OrderForm;
import com.example.bookorder.services.OrderService;
import com.example.bookorder.utils.ResultObject;
import com.example.bookorder.utils.Utils;
import com.example.bookorder.utils.ValidationResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/${version.name}/orders")
public class OrderController extends BaseController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "View order service", notes = "You can view order")
    public ResponseEntity view(@PathVariable String id) {

        try {
            OrderForm orderForm = orderService.findById(id);
            return new ResponseEntity(ResultObject.success(orderForm), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create order service", notes = "You can create order")
    public ResponseEntity create(@Valid @RequestBody OrderForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ValidationResult validationResult = Utils.getBindingResult(bindingResult);
            return new ResponseEntity(ResultObject.failure(validationResult.getMessages()), HttpStatus.BAD_REQUEST);
        }

        try {
            OrderForm orderForm = orderService.createOrder(form);
            return new ResponseEntity(ResultObject.success(orderForm), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Complete order service", notes = "You can complete order")
    public ResponseEntity completeOrder(@PathVariable String id) {

        try {
            OrderForm orderForm = orderService.completeOrder(id);
            return new ResponseEntity(ResultObject.success(orderForm), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @ApiOperation(value = "Get orders by dates", notes = "You can get orders by date, date format : yyyy-MM-dd HH:mm:ss ")
    public ResponseEntity getOrdersByDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                          @RequestParam("limit") int limit, @RequestParam("offset") int offset) {

        try {
            List<OrderForm> orders = orderService.getOrdersByDate(startDate, endDate, limit, offset);
            return new ResponseEntity(ResultObject.success(orders), HttpStatus.OK);
        } catch (WrongDateFormatException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
