package com.example.bookorder.services;

import com.example.bookorder.models.entities.Book;
import com.example.bookorder.models.entities.Customer;
import com.example.bookorder.models.entities.Order;
import com.example.bookorder.models.entities.OrderedBook;
import com.example.bookorder.models.enums.OrderStatus;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.WrongDateFormatException;
import com.example.bookorder.models.forms.OrderForm;
import com.example.bookorder.models.forms.OrderedBookForm;
import com.example.bookorder.repositories.OrderRepository;
import com.example.bookorder.services.impl.BookServiceImpl;
import com.example.bookorder.services.impl.CustomerServiceImpl;
import com.example.bookorder.services.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookServiceImpl bookService;

    @Mock
    private CustomerServiceImpl customerService;

    @Test
    public void createOrder() throws EntityNotFoundException {

        when(bookService.findById(Mockito.anyString())).thenReturn(getBook());
        when(customerService.findById(Mockito.anyString())).thenReturn(getCustomer());
        OrderForm orderForm = new OrderForm();
        orderForm.setCustomerId("1");
        orderForm.setBooks(getOrderedBooksForm());
        when(orderRepository.insert(getOrder(false))).thenReturn(getOrder(true));

        OrderForm orderFormCreated = orderService.createOrder(orderForm);

        Assert.assertNotNull(orderFormCreated);
        Assert.assertEquals("1", orderFormCreated.getId());
        Assert.assertEquals(30, orderFormCreated.getTotalPrice(),1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void completeOrderException() throws EntityNotFoundException {
        Optional<Order> optionalOrder = Optional.empty();
        when(orderRepository.findById(Mockito.anyString())).thenReturn(optionalOrder);

        orderService.completeOrder("1");
    }

    @Test
    public void getOrdersByCustomerId() {
        when(orderRepository.findByCustomerId(Mockito.anyString())).thenReturn(Collections.singletonList(getOrder(true)));

        List<OrderForm> orderForms = orderService.findByCustomerId("1");

        Assert.assertNotEquals(orderForms, new ArrayList<>());
    }

    @Test
    public void getOrdersByCustomerIdEmpty() {
        when(orderRepository.findByCustomerId(Mockito.anyString())).thenReturn(new ArrayList<>());

        List<OrderForm> orderForms = orderService.findByCustomerId("1");

        Assert.assertEquals(orderForms, new ArrayList<>());
    }

    @Test(expected = WrongDateFormatException.class)
    public void getOrdersByDateWrongFormatDate() throws WrongDateFormatException {
        orderService.getOrdersByDate("239343asd","2022-01-18 13:10:00", 0, 1);
    }

    private Book getBook() {
        Book book = new Book();
        book.setStock(100);
        book.setPrice(1);
        book.setName("Book1");
        book.setId("1");
        return book;
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setPhoneNumber("55555555");
        customer.setEmail("ugur.t1993@hotmail.com");
        customer.setFirstName("Uğur");
        customer.setLastName("Türkyılmaz");
        customer.setId("1");
        return customer;
    }

    private List<OrderedBookForm> getOrderedBooksForm() {
        List<OrderedBookForm> orderedBookForms = new ArrayList<>();

        OrderedBookForm orderedBookForm = new OrderedBookForm();
        orderedBookForm.setQuantity(10);
        orderedBookForm.setBookId("1");
        orderedBookForms.add(orderedBookForm);

        OrderedBookForm orderedBookForm2 = new OrderedBookForm();
        orderedBookForm2.setQuantity(20);
        orderedBookForm2.setBookId("2");
        orderedBookForms.add(orderedBookForm2);

        return orderedBookForms;
    }

    private List<OrderedBook> getOrderedBooks() {
        List<OrderedBook> orderedBooks = new ArrayList<>();

        OrderedBook orderedBook = new OrderedBook();
        orderedBook.setQuantity(10);
        orderedBook.setBook(getBook());
        orderedBooks.add(orderedBook);

        OrderedBook orderedBook2 = new OrderedBook();
        orderedBook2.setQuantity(20);
        orderedBook2.setBook(getBook());
        orderedBooks.add(orderedBook2);

        return orderedBooks;
    }


    private Order getOrder(boolean isWithId) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(30);
        if(isWithId) {
            order.setId("1");
        }
        order.setBooks(getOrderedBooks());

        order.setCustomer(getCustomer());
        return order;
    }
}
