package com.example.bookorder.services;

import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.WrongDateFormatException;
import com.example.bookorder.models.forms.OrderForm;
import com.example.bookorder.models.forms.StatisticsForm;

import java.util.List;

public interface OrderService {

    OrderForm createOrder(OrderForm orderForm) throws EntityNotFoundException;

    OrderForm completeOrder(String orderId) throws EntityNotFoundException;

    OrderForm findById(String id) throws EntityNotFoundException;

    List<OrderForm> findByCustomerId(String customerId);

    List<OrderForm> getOrdersByDate(String startDate, String endDate, int limit, int offset) throws WrongDateFormatException;

    List<StatisticsForm> getStatistics(String customerId);
}
