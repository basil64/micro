package com.infinitum.service;


import com.infinitum.exceptions.AuthorNotFoundException;
import com.infinitum.mappers.AuthorEntityMapper;
import com.infinitum.model.entities.Author;
import com.infinitum.model.entities.Book;
import com.infinitum.repositories.AuthorRepository;
import com.infinitum.repositories.BookRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorEntityMapper authorEntityMapper;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, AuthorEntityMapper authorEntityMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorEntityMapper = authorEntityMapper;
    }

    @Transactional
    public com.infinitum.bookstore.generated.model.Author createAuthor(com.infinitum.bookstore.generated.model.Author author) {
        String bookUUID = UUID.randomUUID().toString();
        String authorUUID = UUID.randomUUID().toString();
        String uuid;
        Author authorEntity = authorRepository.findByUuid(author.getAuthorDetails().getUuid().toString()).orElseGet(() -> {
                    author.getAuthorDetails().uuid(UUID.fromString(authorUUID));
                    return authorRepository.save(new Author()
                            .firstName(author.getAuthorDetails().getFirstName())
                            .lastName(author.getAuthorDetails().getLastName())
                            .uuid(authorUUID)
                    );
                }
        );
        uuid = authorEntity.getUuid();
        author.getBooks().forEach(book -> {
            bookRepository.findByUuid(book.getUuid().toString()).orElseGet(() -> {
                book.setUuid(UUID.fromString(bookUUID));
                Book bookEntity = new Book()
                        .title(book.getTitle())
                        .isbn(book.getIsbn())
                        .author(authorEntity)
                        .uuid(bookUUID);
                authorEntity.addBook(bookEntity);
                return bookEntity;
            });
        });

        authorRepository.save(authorEntity);
        author.getAuthorDetails().uuid(UUID.fromString(uuid));
        return author;
    }

    @Transactional
    public com.infinitum.bookstore.generated.model.Author updateAuthor(com.infinitum.bookstore.generated.model.Author author) {
        Author authorEntity = authorRepository
                .findByUuid(author.getAuthorDetails().getUuid().toString())
                .orElseThrow(() -> new AuthorNotFoundException(author.getAuthorDetails().getUuid()));
        authorRepository.save(authorEntity);
        return author;
    }

    public List<com.infinitum.bookstore.generated.model.Author> getAllAuthor() {
        return authorRepository
                .findAll()
                .stream()
                .map(authorEntityMapper::map)
                .collect(Collectors.toList());
    }

    public com.infinitum.bookstore.generated.model.Author getAuthorByUuid(UUID uuid) {
        return authorRepository
                .findByUuid(uuid.toString())
                .map(authorEntityMapper::map)
                .orElseThrow(() -> new AuthorNotFoundException(uuid));
    }
}
