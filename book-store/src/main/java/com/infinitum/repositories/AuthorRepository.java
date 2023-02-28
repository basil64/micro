package com.infinitum.repositories;

import com.infinitum.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);
    Optional<Author> findByUuid(String uuid);
}
