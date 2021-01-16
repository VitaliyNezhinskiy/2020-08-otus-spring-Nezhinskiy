package ru.otus.repository.plain;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.event.MongoBookCascadeSaveEventListener;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.List;


@DataMongoTest
@Import(MongoBookCascadeSaveEventListener.class)
public class MongoCommentRepositoryWithoutCascadeDeleteListenerTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void shouldHaveGotCascadeDelete() {
        val book = Book.builder()
                .id("2").title("T")
                .authors(List.of(new Author("2", "FIO")))
                .genre(new Genre("2", "N"))
                .comments(List.of(Comment.builder()
                        .bookId("2").nickname("N")
                        .message("M").build()))
                .build();

        bookRepository.save(book);

        val bookFromDb = bookRepository.findById("2").get();
        String commentId = bookFromDb.getComments().get(0).getId();

        val sizeBeforeDelete = commentRepository.findAllByBookId("2").size();
        bookRepository.deleteById("2");

        val sizeAfterDelete = commentRepository.findAllByBookId("2").size();

        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete);
    }
}
