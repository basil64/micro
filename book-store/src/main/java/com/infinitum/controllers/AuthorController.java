package com.infinitum.controllers;

import com.infinitum.bookstore.generated.api.AuthorsApi;
import com.infinitum.bookstore.generated.model.Author;
import com.infinitum.service.AuthorService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AuthorController implements AuthorsApi {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthor());
    }

    @Override
    public ResponseEntity<Author> createAuthor(@ApiParam(value = "Author object to creat") @Valid @RequestBody(required = false) Author author) {
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    @Override
    public ResponseEntity<Author> updateAuthor(@ApiParam(value = "Author object to creat") @Valid @RequestBody(required = false) Author author) {
        return ResponseEntity.ok(authorService.updateAuthor(author));
    }

    @Override
    public ResponseEntity<Author> getAuthorByUuid(@ApiParam(value = "", required = true) @PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(authorService.getAuthorByUuid(uuid)
                .add(linkTo(methodOn(AuthorController.class)
                        .getAuthorByUuid(uuid))
                        .withSelfRel()));
    }
}
