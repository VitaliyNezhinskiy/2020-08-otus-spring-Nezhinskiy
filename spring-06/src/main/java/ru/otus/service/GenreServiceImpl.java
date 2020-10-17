package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return genreDao.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }

    }

    @Override
    public long insert(Genre genre) {
        String genreName = genre.getName();
        Optional<Genre> genreOptional = findByName(genreName);
        return genreOptional.map(Genre::getId).orElseGet(() -> genreDao.insert(genre));
    }

    @Override
    public int countGenres() {
        return 0;
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try {
            return genreDao.findByName(name);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
