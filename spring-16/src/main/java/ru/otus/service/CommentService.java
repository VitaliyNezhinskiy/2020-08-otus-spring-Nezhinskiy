package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

public interface CommentService {
    void leaveComment(Book book, String nickname, String message);
    void deleteComment(Comment comment);
    void deleteCommentByNickname(Book book, String nickname);
}
