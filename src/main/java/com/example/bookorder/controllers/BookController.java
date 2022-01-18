package com.example.bookorder.controllers;

import com.example.bookorder.models.exceptions.EntityNotFoundException;
import com.example.bookorder.models.exceptions.MissingDataException;
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
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ApiOperation(value = "Create book service", notes = "You can create book")
    public ResponseEntity insert(@Valid @RequestBody BookForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationResult validationResult = Utils.getBindingResult(bindingResult);
            return new ResponseEntity(ResultObject.failure(validationResult.getMessages()), HttpStatus.BAD_REQUEST);
        }

        BookForm bookForm = bookService.register(form);
        return new ResponseEntity(ResultObject.success(bookForm), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update book quantity service", notes = "You can update quantity of the book")
    public ResponseEntity updateQuantity(@RequestBody BookForm form, @PathVariable String id) {

        try {
            BookForm bookForm = bookService.updateStock(form, id);
            return new ResponseEntity(ResultObject.success(bookForm), HttpStatus.OK);
        } catch (MissingDataException | EntityNotFoundException e) {
            return new ResponseEntity(ResultObject.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
