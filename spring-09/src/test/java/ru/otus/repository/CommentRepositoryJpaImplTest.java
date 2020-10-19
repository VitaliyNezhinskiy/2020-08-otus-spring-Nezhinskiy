package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий на основе работы с комментариями ")
@DataJpaTest
@Import({CommentRepositoryJpaImpl.class, BookRepositoryJpaImpl.class})
public class CommentRepositoryJpaImplTest {
    public static final long COMMENT_ID = 1L;
    public static final long BOOK_ID = 1L;
    public static final String NICKNAME = "Vitaliy";
    public static final String MESSAGE = "Best Comment";
    public static final String UPDATED_MESSAGE = "NEW_MESSAGE";

    @Autowired
    EntityManager entityManager;

    @Autowired
    CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    BookRepositoryJpa bookRepositoryJpa;

    @DisplayName(" должен корректно оставлять комментарии")
    @Test
    public void shouldCorrectLeaveComment() {
        Optional<Book> optionalBook = bookRepositoryJpa.getById(COMMENT_ID);

        assertTrue(optionalBook.isPresent());
        Comment comment = new Comment(COMMENT_ID, BOOK_ID, NICKNAME, MESSAGE);
        commentRepositoryJpa.save(comment);

        Optional<Book> updatedOptionalBook = bookRepositoryJpa.getById(BOOK_ID);
        System.out.println(updatedOptionalBook.get().getComments());

        assertThat(updatedOptionalBook)
                .matches(Optional::isPresent)
                .matches(book -> !book.get().getComments().isEmpty())
                .matches(book -> book.get().getComments().contains(comment));
    }

    @DisplayName(" должен корректно удалять комментарии")
    @Test
    public void shouldCorrectDeleteComment() {
        Optional<Book> optionalBook = bookRepositoryJpa.getById(BOOK_ID);

        assertTrue(optionalBook.isPresent());
        Comment comment = new Comment(COMMENT_ID, BOOK_ID, NICKNAME, MESSAGE);
        comment = commentRepositoryJpa.save(comment);

        commentRepositoryJpa.deleteById(comment.getId());

        Optional<Book> afterDeleteCommentOptionalBook = bookRepositoryJpa.getById(BOOK_ID);
        assertThat(afterDeleteCommentOptionalBook)
                .matches(Optional::isPresent);
        assertThat(!afterDeleteCommentOptionalBook.get().getComments().contains(comment));
    }

    @DisplayName(" должен находить корректный комментарий по id")
    @Test
    void shouldFindExpectedBookById() {
        val actualComment = commentRepositoryJpa.getById(COMMENT_ID);
        val expectedComment = entityManager.find(Comment.class, COMMENT_ID);
        assertThat(actualComment).isPresent().get().isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName(" должен изменять комментарий")
    @Test
    void shouldCorrectUpdateBookTitleById() {
        val comment = entityManager.find(Comment.class, COMMENT_ID);
        String oldMessage = comment.getMessage();
        entityManager.detach(comment);
        comment.setMessage(UPDATED_MESSAGE);
        commentRepositoryJpa.update(comment);
        val updateComment = entityManager.find(Comment.class, COMMENT_ID);
        assertThat(updateComment.getMessage()).isNotEqualTo(oldMessage).isEqualTo(UPDATED_MESSAGE);
    }
}