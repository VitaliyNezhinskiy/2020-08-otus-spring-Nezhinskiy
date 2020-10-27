package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.CommentRepositoryJpa;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepositoryJpa commentRepositoryJpa;

    @Transactional
    @Override
    public void leaveComment(Book book, String nickname, String message) {
        Comment comment = new Comment(0L, book.getId(), nickname, message);
        commentRepositoryJpa.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment) {
        commentRepositoryJpa.deleteById(comment.getId());
    }
}
