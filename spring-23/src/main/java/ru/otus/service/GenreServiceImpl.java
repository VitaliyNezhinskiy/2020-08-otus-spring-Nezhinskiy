package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getByName(String name) {
        try {
            return genreRepository.getByName(name);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}