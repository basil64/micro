package com.infinitum.controllers;

import com.infinitum.exceptions.AuthorNotFoundException;
import com.infinitum.exceptions.BookNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public BookStoreErrorMessage bookNotFoundException(BookNotFoundException ex, WebRequest request) {
        return new BookStoreErrorMessage(ex.getUuid(), String.format("Book not found: %s", ex.getUuid()));
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public BookStoreErrorMessage authorNotFoundException(AuthorNotFoundException ex, WebRequest request) {
        return new BookStoreErrorMessage(ex.getUuid(), String.format("Author not found: %s", ex.getUuid()));
    }
}
