package ru.otus.repository.withlistener;

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
import ru.otus.event.MongoBookCascadeDeleteEventListener;
import ru.otus.event.MongoBookCascadeSaveEventListener;
import ru.otus.repository.BookRepositoryJpa;
import ru.otus.repository.CommentRepositoryJpa;

import java.util.List;


@DataMongoTest
@Import({MongoBookCascadeSaveEventListener.class, MongoBookCascadeDeleteEventListener.class})
public class MongoCommentRepositoryWithListenerTest {
    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Test
    public void shouldHaveGotCascadeDelete() {
        val book = new Book("2", "T",
                List.of(new Author("2", "FIO")),
                new Genre("2", "N"),
                List.of(new Comment("2","N", "M")));

        bookRepositoryJpa.save(book);

        val bookFromDb = bookRepositoryJpa.findById("2").get();
        String commentId = bookFromDb.getComments().get(0).getId();

        val sizeBeforeDelete = commentRepositoryJpa.findAllByBookId("2").size();
        bookRepositoryJpa.deleteById("2");

        val sizeAfterDelete = commentRepositoryJpa.findAllByBookId("2").size();

        Assertions.assertEquals(sizeBeforeDelete - 1, sizeAfterDelete);
    }
}
