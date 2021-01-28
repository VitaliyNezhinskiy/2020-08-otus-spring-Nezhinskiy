package ru.otus.service;

import ru.otus.domain.Author;

import java.util.Optional;

public interface AuthorService {
    Optional<Author> getByFio(String fio);
}
