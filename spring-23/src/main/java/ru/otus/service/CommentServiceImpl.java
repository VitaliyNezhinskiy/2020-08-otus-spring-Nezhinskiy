package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

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
        commentRepository.save(comment);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment) {
        commentRepository.deleteById(comment.getId());
    }

    @Override
    public void deleteCommentByNickname(Book book, String nickname) {
        book.getComments().stream()
                .filter(comment -> comment.getNickname().equals(nickname))
                .forEach(this::deleteComment);
    }
}
