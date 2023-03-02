package com.infinitum.validations;

import com.infinitum.bookstore.generated.model.Book;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<IsbnValidation, Book> {

    public static final int ISBN_SIZE = 6;

    @Override
    public boolean isValid(Book book, ConstraintValidatorContext constraintValidatorContext) {
        return book.getBookDetails() != null
                && book.getBookDetails().getIsbn() != null
                && book.getBookDetails().getIsbn().length() == ISBN_SIZE;
    }
}
