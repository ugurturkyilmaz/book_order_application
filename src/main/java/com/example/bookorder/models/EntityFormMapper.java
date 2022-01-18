package com.example.bookorder.models;

import com.example.bookorder.models.entities.Book;
import com.example.bookorder.models.entities.Customer;
import com.example.bookorder.models.entities.Order;
import com.example.bookorder.models.entities.OrderedBook;
import com.example.bookorder.models.enums.OrderStatus;
import com.example.bookorder.models.forms.BookForm;
import com.example.bookorder.models.forms.CustomerForm;
import com.example.bookorder.models.forms.OrderForm;
import com.example.bookorder.models.forms.OrderedBookForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityFormMapper {

    public static CustomerForm toCustomerForm(Customer customer) {
        CustomerForm customerForm = new CustomerForm();
        customerForm.setFirstName(customer.getFirstName());
        customerForm.setLastName(customer.getLastName());
        customerForm.setPhoneNumber(customer.getPhoneNumber());
        customerForm.setEmail(customer.getEmail());
        customerForm.setId(customer.getId());
        return customerForm;
    }

    public static Customer toCustomer(CustomerForm customerForm) {
        Customer customer = new Customer();
        customer.setFirstName(customerForm.getFirstName());
        customer.setLastName(customerForm.getLastName());
        customer.setPhoneNumber(customerForm.getPhoneNumber());
        customer.setEmail(customerForm.getEmail());
        return customer;
    }

    public static BookForm toBookForm(Book book) {
        BookForm bookForm = new BookForm();
        bookForm.setId(book.getId());
        bookForm.setName(book.getName());
        bookForm.setQuantity(book.getStock());
        bookForm.setPrice(book.getPrice());
        return bookForm;
    }

    public static Book toBook(BookForm bookForm) {
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setStock(bookForm.getQuantity());
        book.setPrice(bookForm.getPrice());
        return book;
    }

    public static OrderForm toOrderForm(Order order) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderDate(order.getOrderDate());
        orderForm.setStatus(order.getStatus());
        orderForm.setId(order.getId());
        orderForm.setCustomer(toCustomerForm(order.getCustomer()));

        List<OrderedBookForm> orderedBookForms = new ArrayList<>();
        for(OrderedBook orderedBook : order.getBooks()) {
            orderedBookForms.add(toOrderedBookForm(orderedBook));
        }
        orderForm.setBooks(orderedBookForms);
        orderForm.setTotalPrice(order.getTotalPrice());
        return orderForm;
    }

    public static Order toOrder() {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.CREATED);
        return order;
    }

    private static OrderedBookForm toOrderedBookForm(OrderedBook orderedBook) {
        OrderedBookForm orderedBookForm = new OrderedBookForm();
        orderedBookForm.setBook(toBookForm(orderedBook.getBook()));
        orderedBookForm.setQuantity(orderedBook.getQuantity());
        orderedBookForm.setPrice(orderedBook.getPrice());
        return orderedBookForm;
    }
}
