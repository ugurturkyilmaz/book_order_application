package com.example.bookorder.services;

import com.example.bookorder.models.entities.Book;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.MissingDataException;
import com.example.bookorder.models.forms.BookForm;
import com.example.bookorder.repositories.BookRepository;
import com.example.bookorder.services.impl.BookServiceImpl;
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
public class BookServiceUnitTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void testRegister() {
        Book book = getBook(false);

        Book createdBook = getBook(true);
        when(bookRepository.insert(book)).thenReturn(createdBook);

        BookForm bookForm = getBookForm(false);
        BookForm createdBook1 = bookService.register(bookForm);

        Assert.assertNotNull(createdBook1);
        Assert.assertEquals("1", createdBook1.getId());
    }

    @Test
    public void testUpdateStock() {

        Book book = getBook(true);

        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(Mockito.anyString())).thenReturn(optionalBook);

        Book differentStockBook = getBook(true);
        differentStockBook.setStock(20);
        when(bookRepository.save(book)).thenReturn(differentStockBook);

        try {
            BookForm bookForm = getBookForm(false);
            bookForm.setStock(20);
            bookForm = bookService.updateStock(bookForm,"1");
            Assert.assertNotNull(bookForm);
            Assert.assertEquals(20, bookForm.getStock().longValue());
        } catch (MissingDataException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test(expected = MissingDataException.class)
    public void testMissingDataExceptionUpdateStock() throws MissingDataException,EntityNotFoundException {

        Book book = getBook(true);

        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(Mockito.anyString())).thenReturn(optionalBook);

        BookForm bookForm = getBookForm(false);
        bookForm.setStock(-10);
        bookService.updateStock(bookForm,"1");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testMissingDataExceptionGetBook() throws EntityNotFoundException {

        Optional<Book> optionalBook = Optional.empty();
        when(bookRepository.findById(Mockito.anyString())).thenReturn(optionalBook);

        bookService.findById("100");
    }

    private Book getBook(boolean isWithId) {
        Book book = new Book();
        book.setStock(100);
        book.setPrice(100);
        book.setName("Book1");
        if(isWithId) {
            book.setId("1");
        }
        return book;
    }

    private BookForm getBookForm(boolean isWithId) {
        BookForm bookForm = new BookForm();
        bookForm.setPrice(100D);
        bookForm.setStock(100);
        bookForm.setName("Book1");
        if(isWithId) {
            bookForm.setId("1");
        }
        return bookForm;
    }
}
