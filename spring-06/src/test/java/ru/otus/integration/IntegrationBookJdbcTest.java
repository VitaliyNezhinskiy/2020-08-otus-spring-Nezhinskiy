package ru.otus.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.AuthorJdbc;
import ru.otus.dao.BookJdbc;
import ru.otus.dao.GenreJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Service для работы с книгами должен:")
@JdbcTest
@Import({AuthorServiceImpl.class, BookServiceImpl.class, GenreServiceImpl.class,
        AuthorJdbc.class, BookJdbc.class, GenreJdbc.class})
public class IntegrationBookJdbcTest {
    public static final String DEFAULT_TITLE = "Only tittle";

    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;

    @DisplayName(" корректно добавлять книгу с уже имеющимся автором и жанром в бд")
    @Test
    void shouldCorrectInsertBookWithAuthorAlreadyInDb() {
        final int expectedGenresCount = genreService.countGenres();
        final int expectedAuthorsCount = authorService.countAuthors();

        Book expected = new Book(DEFAULT_TITLE,
                new Author("Joanne Rowling"),
                new Genre("fantasy"));
        expected.setId(bookService.insert(expected));

        final int actualGenresCount = genreService.countGenres();
        final int actualAuthorsCount = authorService.countAuthors();

        Assertions.assertAll(
                () -> assertEquals(expectedGenresCount, actualGenresCount),
                () -> assertEquals(expectedAuthorsCount, actualAuthorsCount));
    }
}
