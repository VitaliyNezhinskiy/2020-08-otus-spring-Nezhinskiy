package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepositoryJpa;
import ru.otus.repository.CommentRepositoryJpa;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepositoryJpa commentRepositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    @Transactional
    @Override
    public void leaveComment(Book book, String nickname, String message) {
        Comment comment = Comment.builder()
                .bookId(book.getId())
                .nickname(nickname)
                .message(message).build();
        List<Comment> bookComments = book.getComments();
        bookComments.add(comment);
        book.setComments(bookComments);
        bookRepositoryJpa.save(book);
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment) {
        commentRepositoryJpa.deleteById(comment.getId());
    }
}
