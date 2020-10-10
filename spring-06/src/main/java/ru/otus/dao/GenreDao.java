package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(long id);

    long insert(Genre genre);

    int countGenres();

    Optional<Genre> findByName(String name);
}
