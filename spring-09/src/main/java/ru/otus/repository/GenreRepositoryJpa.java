package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryJpa {
    Optional<Genre> getByName(String name);
    List<Genre> getAll();
}
