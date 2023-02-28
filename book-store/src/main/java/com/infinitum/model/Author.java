package com.infinitum.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String description;

    @Column(unique = true, nullable = false)
    private String uuid;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Author firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Author lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Author description(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public String getUuid() {
        return uuid;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books.stream()
                .map(book -> new Book()
                        .uuid(book.getUuid())
                        .title(book.getTitle())
                        .isbn(book.getIsbn())
                        .description(book.getDescription()))
                .collect(Collectors.toList());
    }
}
