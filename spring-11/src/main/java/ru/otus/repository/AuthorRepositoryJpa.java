package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa extends CrudRepository<Author, Long> {
    Optional<Author> getById(long id);

    Optional<Author> getByFio(String fio);

    List<Author> findAll();
}