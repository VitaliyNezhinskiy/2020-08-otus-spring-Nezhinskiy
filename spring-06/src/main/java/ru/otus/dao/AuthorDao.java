package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getById(long id);

    long insert(Author author);

    int countAuthors();

    Optional<Author> findByFio(String fio);
}
