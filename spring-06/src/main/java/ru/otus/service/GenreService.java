package ru.otus.service;

import ru.otus.domain.Genre;

import java.util.Optional;

public interface GenreService {
    Optional<Genre> getById(long id);

    long insert(Genre genre);

    int countGenres();

    Optional<Genre> findByName(String name);
}
