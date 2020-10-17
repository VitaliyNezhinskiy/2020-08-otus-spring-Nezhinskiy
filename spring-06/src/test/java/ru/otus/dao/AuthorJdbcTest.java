package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dao для работы с авторами должно: ")
@JdbcTest
@Import(AuthorJdbc.class)
class AuthorJdbcTest {
    public static final String INSERT_AUTHOR_NAME = "АС Пушкин";
    @Autowired
    private AuthorDao authorDao;

    @DisplayName(" получить верное количество авторов в БД")
    @Test
    void shouldGetCorrectCountAuthorsByFio() {
        assertEquals(authorDao.countAuthors(), 2);
    }


    @DisplayName(" получать корректного автора по ID")
    @Test
    void shouldGetAuthorById() {
        Author expectedAuthor = new Author(1L, "Frank Baum");
        Optional<Author> actualOptionalAuthor = authorDao.getById(1L);
        assertThat(actualOptionalAuthor.orElse(null))
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName(" добвалять нового автора в БД")
    @Test
    void shouldCorrectInsertAuthor() {
        Author expectedAuthor = new Author(INSERT_AUTHOR_NAME);
        expectedAuthor.setId(authorDao.insert(expectedAuthor));
        Author actualAuthor = authorDao.getById(expectedAuthor.getId())
                .orElse(null);
        assertThat(actualAuthor).isEqualToComparingFieldByField(expectedAuthor);
    }
}