package ru.otus.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе работы с книгами")
@DataJpaTest
@Import(BookRepositoryJpaImpl.class)
public class BookRepositoryJpaImplTest {
    private static final long FIRST_BOOK_ID = 1L;
    private static final int EXPECTED_QUERIES_COUNT = 2;
    public static final String NEW_TITLE = "newTitle";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookRepositoryJpaImpl bookRepositoryJpa;

    @DisplayName(" должен корректно сохранять книгу со всей информацией")
    @Test
    void shouldSaveCorrectBookInfo() {
        var author = new Author(0L, "M");
        var genre = new Genre(0L, "F");
        var comment = new Comment(0L, 3L, "V", "bad book");
        Book book = new Book(0L, "D", Collections.singletonList(author),
                genre, Collections.singletonList(comment));
        bookRepositoryJpa.save(book);
        assertThat(book.getId()).isGreaterThan(0);

        var actualBook = entityManager.find(Book.class, book.getId());

        assertThat(actualBook).isNotNull()
                .matches(book1 -> !book1.getTitle().isEmpty())
                .matches(book1 -> book1.getGenre().getId() > 0 && !book1.getGenre().getName().isEmpty())
                .matches(book1 -> book1.getAuthors() != null && book1.getAuthors().size() > 0
                        && book1.getAuthors().get(0).getId() > 0);
    }

    @DisplayName(" должен находить корректную книгу по id")
    @Test
    void shouldFindExpectedBookById() {
        val actualBook = bookRepositoryJpa.getById(FIRST_BOOK_ID);
        val expectedBook = entityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isPresent().get().isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName(" должен возврашать корректное количество книг со всей информацией")
    @Test
    void shouldReturnCorrectAllBooksWithAllInfo() {
        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = bookRepositoryJpa.getAll();

        assertThat(books).isNotNull().hasSize(2)
                .allMatch(book -> !book.getTitle().equals(""))
                .allMatch(book -> book.getGenre().getId() > 0 && !book.getGenre().getName().isEmpty())
                .allMatch(book -> book.getAuthors() != null && book.getAuthors().size() > 0
                        && book.getAuthors().get(0).getId() > 0);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен находить корректную книгу по названию")
    @Test
    void shouldFindCorrectExpectedBookByTitle() {
        val book = entityManager.find(Book.class, FIRST_BOOK_ID);
        List<Book> books = bookRepositoryJpa.getByTitle(book.getTitle());
        assertThat(books).containsOnlyOnce(book);
    }

    @DisplayName(" должен изменять имя заданной книги по id")
    @Test
    void shouldCorrectUpdateBookTitleById() {
        val book = entityManager.find(Book.class, FIRST_BOOK_ID);
        String oldTitle = book.getTitle();
        entityManager.detach(book);
        book.setTitle(NEW_TITLE);
        bookRepositoryJpa.updateBook(book);
        val updatedBook = entityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(NEW_TITLE);
    }

    @DisplayName(" должен удалять заданную книгу по ее id")
    @Test
    void shouldDeleteBookById() {
        val book = entityManager.find(Book.class, FIRST_BOOK_ID);
        entityManager.detach(book);

        bookRepositoryJpa.deleteById(FIRST_BOOK_ID);
        Book deletedBook = entityManager.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }
}