package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {
    Optional<Author> getById(long id);

    Optional<Author> getByFio(String fio);

    List<Author> getAll();
}