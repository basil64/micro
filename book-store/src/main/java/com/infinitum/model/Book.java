package com.infinitum.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(unique = true)
    private String isbn;

    @ManyToOne
    private Author author;

    private String description;

    @ManyToMany(mappedBy = "books")
    private Set<Category> categories = new HashSet<>();

    @Column(unique = true, nullable = false)
    private String uuid;

    public Book() {
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public Book isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public Book author(Author author) {
        this.author = author;
        return this;
    }

    public Book description(String description) {
        this.description = description;
        return this;
    }

    public Book uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
