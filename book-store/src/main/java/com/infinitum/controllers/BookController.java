package com.infinitum.controllers;

import com.infinitum.bookstore.generated.api.BooksApi;
import com.infinitum.bookstore.generated.model.Book;
import com.infinitum.service.BookService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class BookController implements BooksApi {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Override
    public ResponseEntity<Book> addBook(@ApiParam(value = "Book object to add") @Valid @RequestBody(required = false) Book book) {
        return ResponseEntity.ok(bookService.createBookAndAuthor(book));
    }

    @Override
    public ResponseEntity<Book> updateBook(@ApiParam(value = "Update Book") @Valid @RequestBody(required = false) Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @Override
    public ResponseEntity<Book> getBookByUuid(@ApiParam(value = "", required = true) @PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(bookService.getBookByUuid(uuid));
    }
}