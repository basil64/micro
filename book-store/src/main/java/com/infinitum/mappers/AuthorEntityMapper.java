package com.infinitum.mappers;

import com.infinitum.bookstore.generated.model.AuthorDetails;
import com.infinitum.bookstore.generated.model.BookDetails;
import com.infinitum.model.Author;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AuthorEntityMapper {

    public com.infinitum.bookstore.generated.model.Author map(Author authorEntity) {
        com.infinitum.bookstore.generated.model.Author author = new com.infinitum.bookstore.generated.model.Author()
                .authorDetails(new AuthorDetails()
                        .firstName(authorEntity.getFirstName())
                        .lastName(authorEntity.getLastName())
                        .description(authorEntity.getDescription())
                        .uuid(UUID.fromString(authorEntity.getUuid()))
                );
        author.books(authorEntity.getBooks().stream().map(bookEntity -> new BookDetails()
                .title(bookEntity.getTitle())
                .isbn(bookEntity.getIsbn())
                .description(bookEntity.getDescription())
                .uuid(UUID.fromString(bookEntity.getUuid()))
        ).collect(Collectors.toList()));
        return author;
    }
}
