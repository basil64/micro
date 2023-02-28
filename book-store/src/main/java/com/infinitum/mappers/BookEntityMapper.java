package com.infinitum.mappers;


import com.infinitum.bookstore.generated.model.AuthorDetails;
import com.infinitum.bookstore.generated.model.BookDetails;
import com.infinitum.model.Book;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookEntityMapper {

    public com.infinitum.bookstore.generated.model.Book map(Book bookEntity) {
        com.infinitum.bookstore.generated.model.Book book = new com.infinitum.bookstore.generated.model.Book();
        book.setBookDetails(new BookDetails()
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .uuid(UUID.fromString(bookEntity.getUuid()))
                .description(bookEntity.getDescription())
        );
        if (bookEntity.getAuthor() != null) {
            book.setAuthorDetails(new AuthorDetails()
                    .firstName(bookEntity.getAuthor().getFirstName())
                    .lastName(bookEntity.getAuthor().getLastName())
                    .uuid(UUID.fromString(bookEntity.getAuthor().getUuid()))
            );
        }
        return book;
    }
}
