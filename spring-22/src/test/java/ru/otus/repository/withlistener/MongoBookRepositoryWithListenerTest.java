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
import ru.otus.repository.BookRepository;

import java.util.List;

@DisplayName("Репозиторий книг на монге с listener: ")
@DataMongoTest
@Import(MongoBookCascadeSaveEventListener.class)
public class MongoBookRepositoryWithListenerTest {
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("обладает каскадным сохранением")
    @Test
    public void shouldNotHaveCascadeSave() {
        val book = Book.builder().id("2")
                .title("T")
                .authors(List.of(new Author("2", "A")))
                .genre(new Genre("2", "f"))
                .comments(List.of(
                        Comment.builder()
                                .bookId( "2")
                                .nickname( "")
                                .message( "").build()))
                .build();
        bookRepository.save(book);
    }
}
