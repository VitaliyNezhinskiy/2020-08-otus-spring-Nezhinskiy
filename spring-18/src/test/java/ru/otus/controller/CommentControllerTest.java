package ru.otus.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.rest.controllers.BookController;
import ru.otus.rest.controllers.CommentController;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Rest контроллер по работе с комментариями должен")
public class CommentControllerTest {
    @MockBean
    private CommentRepository commentRepository;


    @DisplayName(" возвращать все комментарии")
    @Test
    public void testRouteGetAllBooks() {
        Flux<Comment> commentFlux = Flux.just(Comment.builder().bookId("1").nickname("N").message("M").build(),
                Comment.builder().bookId("1").nickname("N2").message("M2").build());
        CommentController controller = new CommentController(commentRepository);
        when(commentRepository.findAllByBookId("1")).thenReturn(commentFlux);
        WebTestClient client = WebTestClient
                .bindToController(controller).build();

        client.get()
                .uri("/api/books/1/comments")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
