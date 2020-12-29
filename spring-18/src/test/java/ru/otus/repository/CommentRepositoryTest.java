package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.domain.Comment;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий по работе с комментариями должен:")
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName(" корректно находить комментарии по id книги")
    public void shouldFindCommentsBookById() {
        commentRepository.save(Comment.builder()
                .message("M1")
                .nickname("N1")
                .bookId("bookId").build())
                .subscribe();

        commentRepository.save(Comment.builder()
                .message("M2")
                .nickname("N2")
                .bookId("bookId").build())
                .subscribe();


        StepVerifier.create(commentRepository.findAllByBookId("bookId"))
                .assertNext(comment -> assertAll("Should find all comments by book_id",
                        () -> assertNotNull(comment.getId()),
                        () -> assertNotNull(comment.getNickname()),
                        () -> assertEquals("N1", comment.getNickname()),
                        () -> assertEquals("M1", comment.getMessage())))
                .assertNext(comment -> assertAll("Should find all comments by book_id",
                        () -> assertNotNull(comment.getId()),
                        () -> assertNotNull(comment.getNickname()),
                        () -> assertEquals("N2", comment.getNickname()),
                        () -> assertEquals("M2", comment.getMessage())))
                .expectComplete()
                .verify();
    }
}
