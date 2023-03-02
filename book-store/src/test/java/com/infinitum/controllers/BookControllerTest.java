package com.infinitum.controllers;

import com.infinitum.bookstore.generated.model.BookDetails;
import com.infinitum.model.entities.Book;
import com.infinitum.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @LocalServerPort
    int randomServerPort;

    @Test
    void shouldCreateBook() throws URISyntaxException {
        // given
        com.infinitum.bookstore.generated.model.Book book = new com.infinitum.bookstore.generated.model.Book()
                .bookDetails(new BookDetails()
                        .title("Shogun")
                        .isbn("400"));

        final String baseUrl = "http://localhost:" + randomServerPort + "/books";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        HttpEntity<com.infinitum.bookstore.generated.model.Book> request = new HttpEntity<>(book, headers);

        // when
        ResponseEntity<com.infinitum.bookstore.generated.model.Book> result = restTemplate
                .postForEntity(uri, request, com.infinitum.bookstore.generated.model.Book.class);

        // then
        Optional<Book> bookEntity = bookRepository.findByUuid(result
                .getBody()
                .getBookDetails()
                .getUuid()
                .toString());
        assertThat(bookEntity).isPresent();
    }
}
