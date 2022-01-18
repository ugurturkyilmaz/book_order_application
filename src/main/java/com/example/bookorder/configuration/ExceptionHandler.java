package com.example.bookorder.configuration;

import com.example.bookorder.utils.ResultObject;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String name = ex.getParameterName();
        ResultObject resultObject = ResultObject.failure("Parameter is missing: " + name);
        return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String methods = "";
        for (String method : ex.getSupportedMethods()) {
            methods += method + " ,";
        }
        methods = methods.substring(0, methods.length() - 1);
        ResultObject resultObject = ResultObject.failure("HTTP method is wrong.Correct HTTP method is " + methods);
        return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResultObject resultObject = ResultObject.failure("Please enter values correctly");
        return new ResponseEntity(resultObject, org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResultObject resultObject = ResultObject.failure("An error occurred");
        return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResultObject resultObject = ResultObject.failure("One variable type is wrong.Correct is: " + ex.getRequiredType().getSimpleName());
        return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResultObject resultObject;
        if (status == HttpStatus.NOT_FOUND) {
            resultObject = ResultObject.failure("Invalid url");
            return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
        } else {
            resultObject = ResultObject.failure(ex.getMessage());
            return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
        }
    }

    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResultObject resultObject = ResultObject.failure("Missing id");
        return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResultObject resultObject = ResultObject.failure("An error occurred");
        return new ResponseEntity(resultObject, HttpStatus.BAD_REQUEST);
    }

}
