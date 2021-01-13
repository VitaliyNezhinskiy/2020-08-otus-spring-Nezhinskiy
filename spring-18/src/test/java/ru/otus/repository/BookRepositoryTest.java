package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Репозиторий по работе с книгами должен:")
@DataMongoTest
public class BookRepositoryTest {

    private static final String BEST_GENRE = "Best genre";
    private static final String BEST_TITLE = "Best title";
    private static final String BEST_AUTHOR = "Best author";
    public static final String BEST_ID = "Best id";
    public static final String BEST_ID2 = "Best id2";

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName(" корректно сохранять название")
    public void shouldSetTitleOnSave() {
        Mono<Book> bookMono = bookRepository.save(Book.builder()
                .title(BEST_TITLE).build());

        StepVerifier.create(bookMono)
                .assertNext(book ->
                        assertAll("Should save title",
                                () -> assertNotNull(book.getTitle()),
                                () -> assertEquals(BEST_TITLE, book.getTitle())))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName(" корректно сохранять жанр")
    public void shouldSetGenreOnSave() {
        Mono<Book> bookMono = bookRepository.save(Book.builder()
                .genre(Genre.builder().name(BEST_GENRE).build()).build());

        StepVerifier.create(bookMono)
                .assertNext(book -> assertAll("Should save genre",
                        () -> assertNotNull(book.getGenre()),
                        () -> assertNotNull(book.getGenre().getName()),
                        () -> assertEquals(BEST_GENRE, book.getGenre().getName())))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName(" корректно сохранять автора")
    public void shouldSetAuthorsOnSave() {
        Mono<Book> bookMono = bookRepository.save(Book.builder()
                .authors(List.of(Author.builder().fio(BEST_AUTHOR).build()))
                .build());

        StepVerifier.create(bookMono)
                .assertNext(book -> assertAll("Should save author",
                        () -> assertNotNull(book.getAuthors()),
                        () -> assertFalse(book.getAuthors().isEmpty()),
                        () -> assertNotNull(book.getAuthors().get(0).getFio()),
                        () -> assertEquals(BEST_AUTHOR, book.getAuthors().get(0).getFio())))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName(" корректно находить книгу по ID")
    public void shouldFindBookById() {
        bookRepository.save(Book.builder()
                .id(BEST_ID)
                .title(BEST_TITLE)
                .genre(Genre.builder().name(BEST_GENRE).build())
                .authors(List.of(Author.builder().fio(BEST_AUTHOR).build()))
                .build()).block();

        StepVerifier.create(bookRepository.findById(BEST_ID))
                .assertNext(book -> assertAll("Should find book by id",
                        () -> assertNotNull(book.getId()),
                        () -> assertEquals(BEST_ID, book.getId()),
                        () -> assertNotNull(book.getTitle()),
                        () -> assertEquals(BEST_TITLE, book.getTitle()),
                        () -> assertNotNull(book.getAuthors()),
                        () -> assertFalse(book.getAuthors().isEmpty()),
                        () -> assertNotNull(book.getAuthors().get(0).getFio()),
                        () -> assertEquals(BEST_AUTHOR, book.getAuthors().get(0).getFio()),
                        () -> assertNotNull(book.getGenre()),
                        () -> assertNotNull(book.getGenre().getName()),
                        () -> assertEquals(BEST_GENRE, book.getGenre().getName())))
                .expectComplete()
                .verify();
    }


    @Test
    @DisplayName(" корректно удалять книгу по ID")
    public void shouldDeleteBookById() {
        bookRepository.save(Book.builder()
                .id(BEST_ID2)
                .title(BEST_TITLE)
                .genre(Genre.builder().name(BEST_GENRE).build())
                .authors(List.of(Author.builder().fio(BEST_AUTHOR).build()))
                .build()).block();

        bookRepository.deleteById(BEST_ID2).block();

        StepVerifier.create(bookRepository.findById(BEST_ID2))
                .expectNextCount(0).verifyComplete();
    }
}