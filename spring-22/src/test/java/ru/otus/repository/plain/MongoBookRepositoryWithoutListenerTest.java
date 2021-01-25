package ru.otus.repository.plain;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mapping.MappingException;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Репозиторий книг на монге: ")
@DataMongoTest
public class MongoBookRepositoryWithoutListenerTest {
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("не обладает каскадным сохранением")
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

        assertThatThrownBy(() -> bookRepository.save(book))
                .isInstanceOf(MappingException.class);
    }
}
