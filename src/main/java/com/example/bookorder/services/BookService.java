package com.example.bookorder.services;

import com.example.bookorder.models.entities.Book;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.MissingDataException;
import com.example.bookorder.models.exceptions.StockNotEnoughException;
import com.example.bookorder.models.forms.BookForm;

public interface BookService {

    BookForm register(BookForm bookForm);

    BookForm updateStock(BookForm bookForm, String id) throws MissingDataException, EntityNotFoundException;

    void changeStock(String bookId, boolean isAdd) throws StockNotEnoughException, EntityNotFoundException;

    Book findById(String id) throws EntityNotFoundException;
}
