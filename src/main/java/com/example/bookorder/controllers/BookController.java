package com.example.bookorder.controllers;

import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.MissingDataException;
import com.example.bookorder.models.exceptions.StockNotEnoughException;
import com.example.bookorder.models.forms.BookForm;
import com.example.bookorder.services.BookService;
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

@Controller
@RequestMapping("/${version.name}/books")
public class BookController extends BaseController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create book service", notes = "You can create book")
    public ResponseEntity insert(@Valid @RequestBody BookForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationResult validationResult = Utils.getBindingResult(bindingResult);
            return new ResponseEntity(ResultObject.failure(validationResult.getMessages()), HttpStatus.BAD_REQUEST);
        }

        BookForm bookForm = bookService.register(form);
        return new ResponseEntity(ResultObject.success(bookForm), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}",produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Update book stock service", notes = "You can update stock of the book")
    public ResponseEntity updateStock(@RequestBody BookForm form, @PathVariable String id) {

        try {
            BookForm bookForm = bookService.updateStock(form, id);
            return new ResponseEntity(ResultObject.success(bookForm), HttpStatus.OK);
        } catch (MissingDataException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/reserve/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Reserve or release book", notes = "You can reserve or release book and it changes stock of books")
    public ResponseEntity reserveOrReleaseBook(@PathVariable String id, @RequestParam("isAdd") boolean isAdd) {

        try {
            bookService.changeStock(id, isAdd);
            return new ResponseEntity(ResultObject.success(true), HttpStatus.OK);
        } catch (StockNotEnoughException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
