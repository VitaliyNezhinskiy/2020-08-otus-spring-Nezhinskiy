package ru.otus.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.rest.controllers.BookController;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Rest контроллер по работе с книгами должен")
public class BookControllerTest {

    private static final String BOOK_ID = "bookId";
    private static final String BOOK_ID2 = "bookId2";
    private static final Book TEST_BOOK = Book.builder().id(BOOK_ID)
            .title("bookTitle").genre(Genre.builder().id("genreId").name("genreName").build())
            .authors(List.of(Author.builder().id("authorId").fio("authorFio").build()))
            .build();
    private static final Book TEST_BOOK_2 = Book.builder().id(BOOK_ID2)
            .title("bookTitle2").genre(Genre.builder().id("genreId2").name("genreName2").build())
            .authors(List.of(Author.builder().id("authorId2").fio("authorFio2").build()))
            .build();

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;


    @DisplayName(" возвращать книгу по id")
    @Test
    public void testRouteGet() {
        Mono<Book> bookMono = Mono.just(TEST_BOOK);
        BookController controller = new BookController(bookRepository, authorRepository, genreRepository);
        when(bookRepository.findById(BOOK_ID))
                .thenReturn(bookMono);

        WebTestClient client = WebTestClient
                .bindToController(controller).build();
        client.get()
                .uri("/api/books/" + BOOK_ID)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName(" возвращать все книги")
    @Test
    public void testRouteGetAllBooks() {
        Flux<Book> bookFlux = Flux.just(TEST_BOOK, TEST_BOOK_2);
        BookController controller = new BookController(bookRepository, authorRepository, genreRepository);
        when(bookRepository.findAll()).thenReturn(bookFlux);
        WebTestClient client = WebTestClient
                .bindToController(controller).build();

        client.get()
                .uri("/api/books/")
                .exchange()
                .expectStatus()
                .isOk();
    }
}