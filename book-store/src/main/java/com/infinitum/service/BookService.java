package com.infinitum.service;


import com.infinitum.exceptions.BookNotFoundException;
import com.infinitum.mappers.BookEntityMapper;
import com.infinitum.model.Author;
import com.infinitum.model.Book;
import com.infinitum.repositories.AuthorRepository;
import com.infinitum.repositories.BookRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookEntityMapper bookEntityMapper;

    public BookService(AuthorRepository authorRepository, BookRepository bookRepository, BookEntityMapper bookEntityMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookEntityMapper = bookEntityMapper;
    }

    @Transactional
    public com.infinitum.bookstore.generated.model.Book createBookAndAuthor(com.infinitum.bookstore.generated.model.Book book) {
        Author authorEntity = null;
        String bookUUID = UUID.randomUUID().toString();
        String authorUUID = UUID.randomUUID().toString();
        String uuid = authorUUID;
        if (book.getAuthorDetails() != null) {
            String uuidToFind = book.getAuthorDetails().getUuid() != null ? book.getAuthorDetails().getUuid().toString() : "";
            authorEntity = authorRepository.findByUuid(uuidToFind).orElseGet(() -> {
                Author createdAuthorEntity = new Author()
                        .firstName(book.getAuthorDetails().getFirstName())
                        .lastName(book.getAuthorDetails().getLastName())
                        .uuid(authorUUID);
                return authorRepository.save(createdAuthorEntity);
            });
            uuid = authorEntity.getUuid();
        }
        Book bookEntity = new Book()
                .title(book.getBookDetails().getTitle())
                .isbn(book.getBookDetails().getIsbn())
                .author(authorEntity)
                .uuid(bookUUID);
        bookRepository.save(bookEntity);
        if (authorEntity != null) {
            authorEntity.addBook(bookEntity);
        }
        book.getBookDetails().uuid(UUID.fromString(bookUUID));
        book.getAuthorDetails().uuid(UUID.fromString(uuid));
        return book;
    }

    public List<com.infinitum.bookstore.generated.model.Book> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(bookEntityMapper::map)
                .collect(Collectors.toList());
    }

    public com.infinitum.bookstore.generated.model.Book updateBook(com.infinitum.bookstore.generated.model.Book book) {
        bookRepository.findByUuid(book.getBookDetails().getUuid().toString())
                .ifPresent(bookEntity -> {
                            bookEntity.title(book.getBookDetails().getTitle())
                                    .isbn(book.getBookDetails().getIsbn())
                                    .description(book.getBookDetails().getDescription());
                            bookRepository.save(bookEntity);
                        }
                );
        return book;
    }

    public com.infinitum.bookstore.generated.model.Book getBookByUuid(UUID uuid) {
        return bookRepository
                .findByUuid(uuid.toString())
                .map(bookEntityMapper::map)
                .orElseThrow(() -> new BookNotFoundException(uuid));
    }
}
