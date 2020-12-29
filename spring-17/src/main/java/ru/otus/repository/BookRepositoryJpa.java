package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa extends MongoRepository<Book, String> {
    List<Book> getAllByTitle(String title);
    Optional<Book> getById(String id);
}