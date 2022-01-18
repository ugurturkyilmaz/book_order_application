package com.example.bookorder.services;

import com.example.bookorder.models.entities.Book;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.MissingDataException;
import com.example.bookorder.models.forms.BookForm;
import com.example.bookorder.models.forms.OrderedBookForm;

import java.util.List;

public interface BookService {

    BookForm register(BookForm bookForm);

    BookForm updateStock(BookForm bookForm, String id) throws MissingDataException, EntityNotFoundException;

    void decreaseStocks(List<OrderedBookForm> orderedBooks);

    Book findById(String id) throws EntityNotFoundException;
}
