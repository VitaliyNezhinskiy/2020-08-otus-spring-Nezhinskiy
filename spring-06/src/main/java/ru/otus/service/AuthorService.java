package ru.otus.service;

import ru.otus.domain.Author;

import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(long id);

    long insert(Author author);

    int countAuthors();

    Optional<Author> findByFio(String fio);
}
