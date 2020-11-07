package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Author;

import java.util.Optional;

public interface AuthorRepositoryJpa extends MongoRepository<Author, String> {
    Optional<Author> getByFio(String fio);
}