package ru.otus.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.*;
import ru.otus.service.AuthorServiceImpl;
import ru.otus.service.BookServiceImpl;
import ru.otus.service.GenreServiceImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Service для работы с книгами должен:")
@DataJpaTest
@Import({BookServiceImpl.class, GenreServiceImpl.class, AuthorServiceImpl.class,
        BookRepositoryJpaImpl.class,GenreRepositoryJpaImpl.class, AuthorRepositoryJpaImpl.class })
public class IntegrationBookServiceTest {
    public static final String DEFAULT_TITLE = "Only tittle";

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;
    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;
    @Autowired
    private BookServiceImpl bookService;


    @DisplayName(" корректно добавлять книгу с уже имеющимся автором и жанром в бд")
    @Test
    void shouldCorrectInsertBookWithAuthorAlreadyInDb() {
        final int expectedGenresCount = genreRepositoryJpa.getAll().size();
        final int expectedAuthorsCount = authorRepositoryJpa.getAll().size();

        Book expected = new Book(0L, DEFAULT_TITLE,
                Collections.singletonList(new Author(0L, "Joanne Rowling")),
                new Genre(0L, "fantasy"), Collections.emptyList());

        bookService.saveBook(expected);

        final int actualGenresCount = genreRepositoryJpa.getAll().size();
        final int actualAuthorsCount = authorRepositoryJpa.getAll().size();

        Assertions.assertAll(
                () -> assertEquals(expectedGenresCount, actualGenresCount),
                () -> assertEquals(expectedAuthorsCount, actualAuthorsCount));
    }
}