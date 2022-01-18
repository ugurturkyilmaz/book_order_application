package com.example.bookorder.services.impl;

import com.example.bookorder.models.EntityFormMapper;
import com.example.bookorder.models.entities.Book;
import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.MissingDataException;
import com.example.bookorder.models.exceptions.StockNotEnoughException;
import com.example.bookorder.models.forms.BookForm;
import com.example.bookorder.repositories.BookRepository;
import com.example.bookorder.services.BookService;
import com.example.bookorder.utils.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookForm register(BookForm bookForm) {
        Book book = EntityFormMapper.toBook(bookForm);

        book = bookRepository.insert(book);

        return EntityFormMapper.toBookForm(book);
    }

    @Override
    public BookForm updateStock(BookForm bookForm, String id) throws MissingDataException, EntityNotFoundException {

        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()) {
            if(bookForm.getStock() == null || bookForm.getStock() < 0) {
                throw new MissingDataException(ErrorMessages.BOOK_QUANTITY_MISSING_OR_LESS_THAN_0);
            }
            Book book = optionalBook.get();
            book.setStock(bookForm.getStock());
            book = bookRepository.save(book);
            return EntityFormMapper.toBookForm(book);
        } else {
            throw new EntityNotFoundException(ErrorMessages.BOOK_NOT_FOUND);
        }
    }

    @Override
    public void changeStock(String bookId, boolean isAdd) throws StockNotEnoughException, EntityNotFoundException {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if(!isAdd && book.getStock() < 1) {
                throw new StockNotEnoughException(ErrorMessages.BOOK_STOCK_NOT_ENOUGH);
            }
            if(isAdd) {
                book.setStock(book.getStock() + 1);
            } else {
                book.setStock(book.getStock() - 1);
            }

            bookRepository.save(book);
        } else {
            throw new EntityNotFoundException(ErrorMessages.BOOK_STOCK_NOT_ENOUGH);
        }
    }

    @Override
    public Book findById(String id) throws EntityNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(!optionalBook.isPresent()) {
            throw new EntityNotFoundException(ErrorMessages.BOOK_NOT_FOUND);
        }
        return optionalBook.get();
    }
}
