package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("DAO для работы с книгами должно:")
@JdbcTest
@Import({BookJdbc.class, GenreJdbc.class, AuthorJdbc.class})
class BookJdbcTest {
    public static final int EXPECTED_BOOK_COUNT = 2;
    public static final String DEFAULT_TITLE = "Bad advises";
    public static final String DEFAULT_AUTHOR_NAME = "Zahoder";
    public static final String DEFAULT_GENRE_NAME = "tale";
    public static final String THE_WIZARD_OF_OZ = "The Wizard of Oz";
    public static final long ID_WIZARD_OF_OZ = 1L;

    @Autowired
    private BookDao dao;

    @DisplayName(" возвращать ожидаемое количество книг")
    @Test
    void shouldReturnExpectedBookCount() {
        int count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName(" добавлять book в бд")
    @Test
    void shouldInsertBook() {
        Book expected = new Book(DEFAULT_TITLE,
                new Author(DEFAULT_AUTHOR_NAME),
                new Genre(DEFAULT_GENRE_NAME));
        expected.setId(dao.insert(expected));

        Book actual = dao.getById(expected.getId()).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName(" получать корректную книгу по ID")
    @Test
    void shouldGetBookById() {
        Book expectedBook = new Book(ID_WIZARD_OF_OZ, THE_WIZARD_OF_OZ,
                new Author(1L, "Frank Baum"),
                new Genre(1L, "fairy tail"));

        Book actualBook = dao.getById(1L).orElse(null);
        assertThat(actualBook).isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName(" получить верное количество книг по названию в БД")
    @Test
    void shouldGetCorrectCountBooksByTitle() {
        assertEquals(dao.countBooksByTitle(THE_WIZARD_OF_OZ), 1);
    }

    @DisplayName(" получить количество 0, если книги нет в БД")
    @Test
    void shouldGetZeroCountBooksByNotExistingTitle() {
        assertEquals(dao.countBooksByTitle("Волшебник изумрудного города"), 0);
    }

    @DisplayName(" корректно удалять книгу по ID из БД")
    @Test
    void shouldCorrectDeleteById() {
        dao.deleteById(ID_WIZARD_OF_OZ);
        assertEquals(dao.countBooksByTitle(THE_WIZARD_OF_OZ), 0);

    }

    @DisplayName(" возвращать корректное количество всех книг в БД")
    @Test
    void shouldCorrectGetAll() {
        assertEquals(dao.getAll().size(), 2);
    }

}