package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dao для работы с жанрами должно: ")
@JdbcTest
@Import(GenreJdbc.class)
class GenreJdbcTest {
    private static final String INSERT_GENRE_NAME = "bored horror";

    @Autowired
    private GenreDao genreDao;

    @DisplayName(" получить верное количество жанров по имени в БД")
    @Test
    void shouldGetCorrectCountGenresByName() {
        assertEquals(genreDao.countGenres(), 2);
    }

    @DisplayName(" получать корректный жанр по ID")
    @Test
    void shouldGetGenreById() {
        Genre expectedGenre = new Genre(1L, "fairy tail");
        Genre actualGenre = genreDao.getById(1L).orElse(null);
        assertThat(actualGenre).isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName(" добавлять новый жанр в БД")
    @Test
    void shouldCorrectInsertGenre() {
        Genre expectedGenre = new Genre(INSERT_GENRE_NAME);
        expectedGenre.setId(genreDao.insert(expectedGenre));
        Genre actualGenre = genreDao.getById(expectedGenre.getId())
                .orElse(null);
        assertThat(actualGenre).isEqualToComparingFieldByField(expectedGenre);
    }
}