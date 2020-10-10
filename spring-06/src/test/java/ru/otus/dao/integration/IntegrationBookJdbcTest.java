package ru.otus.dao.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.*;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DAO для работы с книгами должно:")
@JdbcTest
@Import({AuthorJdbc.class, BookJdbc.class, GenreJdbc.class})
public class IntegrationBookJdbcTest {
    public static final String DEFAULT_TITLE = "Only tittle";

    @Autowired
    private BookDao bookDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;

    @DisplayName(" корректно добавлять книгу с уже имеющимся автором и жанром в бд")
    @Test
    void shouldCorrectInsertBookWithAuthorAlreadyInDb() {
        final int expectedGenresCount = genreDao.countGenres();
        final int expectedAuthorsCount = authorDao.countAuthors();

        Book expected = new Book(DEFAULT_TITLE,
                new Author("Joanne Rowling"),
                new Genre("fantasy"));
        expected.setId(bookDao.insert(expected));

        final int actualGenresCount = genreDao.countGenres();
        final int actualAuthorsCount = authorDao.countAuthors();

        Assertions.assertAll(
                () -> assertEquals(expectedGenresCount, actualGenresCount),
                () -> assertEquals(expectedAuthorsCount, actualAuthorsCount));
    }
}
