package com.example.bookorder.services.impl;

import com.example.bookorder.models.EntityFormMapper;
import com.example.bookorder.models.entities.Book;
import com.example.bookorder.models.entities.Order;
import com.example.bookorder.models.entities.OrderedBook;
import com.example.bookorder.models.enums.OrderStatus;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.WrongDateFormatException;
import com.example.bookorder.models.forms.OrderForm;
import com.example.bookorder.models.forms.OrderedBookForm;
import com.example.bookorder.models.forms.StatisticsForm;
import com.example.bookorder.repositories.OrderRepository;
import com.example.bookorder.services.BookService;
import com.example.bookorder.services.CustomerService;
import com.example.bookorder.services.OrderService;
import com.example.bookorder.utils.ErrorMessages;
import com.example.bookorder.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository orderRepository, BookService bookService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @Override
    public OrderForm createOrder(OrderForm orderForm) throws EntityNotFoundException {
        Order order = EntityFormMapper.toOrder();
        order.setCustomer(customerService.findById(orderForm.getCustomerId()));
        order.setBooks(getOrderedBooks(orderForm));
        order.setTotalPrice(order.getBooks().stream().mapToDouble(OrderedBook::getPrice).sum());
        order = orderRepository.insert(order);

        return EntityFormMapper.toOrderForm(order);
    }

    @Override
    public OrderForm completeOrder(String orderId) throws EntityNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(!optionalOrder.isPresent()) {
            throw new EntityNotFoundException(ErrorMessages.ORDER_NOT_FOUND);
        }
        Order order = optionalOrder.get();
        order.setStatus(OrderStatus.COMPLETED);
        order = orderRepository.save(order);
        return EntityFormMapper.toOrderForm(order);
    }

    @Override
    public OrderForm findById(String id) throws EntityNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(!optionalOrder.isPresent()) {
            throw new EntityNotFoundException(ErrorMessages.ORDER_NOT_FOUND);
        }
        return EntityFormMapper.toOrderForm(optionalOrder.get());
    }

    @Override
    public List<OrderForm> findByCustomerId(String customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        if(orders == null) {
            return new ArrayList<>();
        }

        List<OrderForm> orderForms = new ArrayList<>();
        for(Order order : orders) {
            OrderForm orderForm = EntityFormMapper.toOrderForm(order);
            orderForms.add(orderForm);
        }
        return orderForms;
    }

    @Override
    public List<OrderForm> getOrdersByDate(String startDate, String endDate, int limit, int offset) throws WrongDateFormatException {

        Date startDateAsDate = Utils.stringToDate(startDate);
        Date endDateAsDate = Utils.stringToDate(endDate);
        if(startDateAsDate == null || endDateAsDate == null) {
            throw new WrongDateFormatException(ErrorMessages.WRONG_DATE_FORMAT);
        }

        Page<Order> orders = orderRepository.findBetweenDates(startDateAsDate, endDateAsDate, PageRequest.of(offset, limit));

        if(orders == null) {
            return new ArrayList<>();
        }

        List<OrderForm> orderForms = new ArrayList<>();
        for(Order order : orders) {
            orderForms.add(EntityFormMapper.toOrderForm(order));
        }
        return orderForms;
    }

    @Override
    public List<StatisticsForm> getStatistics(String customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if(orders == null) {
            return new ArrayList<>();
        }

        List<StatisticsForm> statistics = new ArrayList<>();

        Map<String, List<Order>> groupByDate = orders.stream().collect(Collectors.groupingBy(x -> getMonthForInt(x.getOrderDate())));
        for(var entry : groupByDate.entrySet()) {
            statistics.add(getStatisticFromMap(entry));
        }
        return statistics;
    }

    private List<OrderedBook> getOrderedBooks(OrderForm orderForm) throws EntityNotFoundException {
        List<OrderedBook> orderedBooks = new ArrayList<>();
        for(OrderedBookForm orderedBookForm : orderForm.getBooks()) {
            OrderedBook orderedBook = new OrderedBook();
            Book book = bookService.findById(orderedBookForm.getBookId());
            orderedBook.setBook(book);
            orderedBook.setQuantity(orderedBookForm.getQuantity());
            orderedBook.setPrice(book.getPrice() * orderedBookForm.getQuantity());
            orderedBooks.add(orderedBook);
        }
        return orderedBooks;
    }

    private String getMonthForInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int num = calendar.get(Calendar.MONTH);
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        return months[num];
    }

    private StatisticsForm getStatisticFromMap(Map.Entry<String, List<Order>> entry) {
        StatisticsForm statistic = new StatisticsForm();
        statistic.setMonth(entry.getKey());
        statistic.setTotalOrderCount(entry.getValue().size());
        int totalBookCount = 0;
        double totalPrice = 0;
        for(Order order : entry.getValue()) {
            totalBookCount += order.getBooks().stream().mapToInt(OrderedBook::getQuantity).sum();
            totalPrice += order.getTotalPrice();
        }
        statistic.setTotalBookCount(totalBookCount);
        statistic.setTotalPurchasedAmount(totalPrice);
        return statistic;
    }
}
