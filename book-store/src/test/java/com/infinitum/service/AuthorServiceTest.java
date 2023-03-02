package com.infinitum.service;

import com.infinitum.mappers.AuthorEntityMapper;
import com.infinitum.model.entities.Author;
import com.infinitum.model.entities.Book;
import com.infinitum.repositories.AuthorRepository;
import com.infinitum.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.infinitum.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Spy
    private AuthorEntityMapper entityMapper;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void shouldCreateAuthor() {
    }

    @Test
    void shouldUpdateAuthor() {
    }

    @Test
    void shouldGetAllAuthor() {
        // given
        String authorUuid1 = UUID.randomUUID().toString();
        Author authorEntity1 = new Author()
                .uuid(authorUuid1)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1);

        Book bookEntity1 = new Book()
                .author(authorEntity1)
                .title(TITLE_1)
                .isbn(ISBN_1)
                .uuid(UUID.randomUUID().toString());

        Book bookEntity2 = new Book()
                .author(authorEntity1)
                .title(TITLE_2)
                .isbn(ISBN_2)
                .uuid(UUID.randomUUID().toString());

        authorEntity1.addBook(bookEntity1);
        authorEntity1.addBook(bookEntity2);

        String authorUuid2 = UUID.randomUUID().toString();
        Author authorEntity2 = new Author()
                .uuid(authorUuid2)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2);

        Book bookEntity3 = new Book()
                .author(authorEntity2)
                .title(TITLE_3)
                .isbn(ISBN_3)
                .uuid(UUID.randomUUID().toString());
        authorEntity2.addBook(bookEntity3);
        // when
        when(authorRepository.findAll()).thenReturn(List.of(authorEntity1, authorEntity2));
        List<com.infinitum.bookstore.generated.model.Author> result = authorService.getAllAuthor();

        // then
        assertThat(result).isNotEmpty();
    }
}
