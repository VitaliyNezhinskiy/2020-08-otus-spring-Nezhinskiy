package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepositoryJpa;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepositoryJpa genreRepositoryJpa;

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getByName(String name) {
        try {
            return genreRepositoryJpa.getByName(name);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}