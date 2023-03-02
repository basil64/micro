package com.infinitum.mappers;

import com.infinitum.bookstore.generated.model.AuthorDetails;
import com.infinitum.bookstore.generated.model.BookDetails;
import com.infinitum.model.entities.Author;
import com.infinitum.model.entities.Book;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.infinitum.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthorEntityMapperTest {
    @ParameterizedTest
    @MethodSource("provideListAndParams")
    void shouldMapEntity(Author authorEntity, com.infinitum.bookstore.generated.model.Author expected) {
        // given
        AuthorEntityMapper mapper = new AuthorEntityMapper();

        // when
        com.infinitum.bookstore.generated.model.Author result = mapper.map(getAuthorEntity());

        // then
        assertThat(result.getAuthorDetails()).usingRecursiveComparison().isEqualTo(expected.getAuthorDetails());
        assertThat(result.getBooks().toArray()).containsExactlyInAnyOrderElementsOf(expected.getBooks());
    }

    private static Stream<Arguments> provideListAndParams() {
        return Stream.of(
                Arguments.of(getAuthorEntity(), getAuthor())
        );
    }

    private static com.infinitum.bookstore.generated.model.Author getAuthor() {
        com.infinitum.bookstore.generated.model.Author expected = new com.infinitum.bookstore.generated.model.Author()
                .authorDetails(new AuthorDetails()
                        .firstName(FIRST_NAME_1)
                        .lastName(LAST_NAME_1)
                        .uuid(UUID.fromString(AUTHOR1_UUID)))
                .books(List.of(new BookDetails()
                                        .title(TITLE_1)
                                        .isbn(ISBN_1)
                                        .description(DESCRIPTION_1)
                                        .uuid(UUID.fromString(BOOK1_UUID)),
                                new BookDetails()
                                        .title(TITLE_2)
                                        .isbn(ISBN_2)
                                        .description(DESCRIPTION_2)
                                        .uuid(UUID.fromString(BOOK2_UUID))
                        )
                );
        return expected;
    }

    private static Author getAuthorEntity() {
        Author authorEntity1 = new Author()
                .uuid(AUTHOR1_UUID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1);

        authorEntity1.addBook(new Book()
                .author(authorEntity1)
                .title(TITLE_1)
                .isbn(ISBN_1)
                .description(DESCRIPTION_1)
                .uuid(BOOK1_UUID));

        authorEntity1.addBook(new Book()
                .author(authorEntity1)
                .title(TITLE_2)
                .isbn(ISBN_2)
                .description(DESCRIPTION_2)
                .uuid(BOOK2_UUID));
        return authorEntity1;
    }
}