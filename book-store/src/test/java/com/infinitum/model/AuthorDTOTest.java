package com.infinitum.model;

import com.infinitum.model.dtos.AuthorDTO;
import com.infinitum.model.entities.Author;
import com.infinitum.model.entities.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AuthorDTOTest {
    Logger LOGGER = Logger.getLogger("AuthorDTOTestLogger");

    @MockBean
    WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldInstantiateEntityManager() {
        assertThat(entityManager).isNotNull();
    }

    @Test
    void shouldRunProjectionQuery() {
        // given
        Author author = new Author()
                .firstName("Mikka")
                .lastName("Waltari")
                .uuid(UUID.randomUUID().toString());

        entityManager.persist(author);

        // when
        List<AuthorDTO> authors = entityManager
                .createQuery("Select a.id as id, a.firstName as firstName, a.lastName From Author a")
                .getResultList();

        // then
        assertThat(authors.size()).isNotEqualTo(0);
    }

    @Test
    void shouldShowNPlusOneProblem() {

        for (int i = 0; i < 3; i++) {
            Author author = new Author()
                    .firstName("Mikka " + i)
                    .lastName("Waltari " + i)
                    .uuid(UUID.randomUUID().toString());

            Book book = new Book()
                    .title("Title " + i)
                    .isbn(100 + i + "")
                    .uuid(UUID.randomUUID().toString())
                    .author(author);
            author.addBook(book);
            entityManager.persist(author);
        }

        List<Book> books = entityManager
                .createQuery("Select b From Book b ", Book.class)
                .getResultList();

        LOGGER.info("Loaded " + books.size() + " books");
        for (Book book : books) {
            LOGGER.info(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
        }
    }
}