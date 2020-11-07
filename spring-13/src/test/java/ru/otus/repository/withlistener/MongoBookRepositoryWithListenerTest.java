package ru.otus.repository.withlistener;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.event.MongoBookCascadeSaveEventListener;
import ru.otus.repository.BookRepositoryJpa;

import java.util.List;

@DisplayName("Репозиторий книг на монге с listener: ")
@DataMongoTest
@Import(MongoBookCascadeSaveEventListener.class)
public class MongoBookRepositoryWithListenerTest {
    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @DisplayName("обладает каскадным сохранением")
    @Test
    public void shouldNotHaveCascadeSave() {
        val book = new Book("3","T",
                List.of(new Author("2", "A")),
                new Genre("2", "f"),
                List.of(new Comment( "3", "", "")));

        bookRepositoryJpa.save(book);
    }
}
