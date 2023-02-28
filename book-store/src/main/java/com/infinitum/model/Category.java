package com.infinitum.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String bookCategory;

    private String description;

    @Column(unique = true, nullable = false)
    private String uuid;

    @ManyToMany
    @JoinTable(name = "book_category", joinColumns=@JoinColumn(name="book_id"), inverseJoinColumns=@JoinColumn(name="category_id"))
    private Set<Book> books = new HashSet<>();

    public Category category(String bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public Category uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUuid() {
        return uuid;
    }
}
