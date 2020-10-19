package ru.otus.service;

import ru.otus.domain.Genre;

import java.util.Optional;

public interface GenreService {
    Optional<Genre> getByName(String name);
}
