package ru.otus.repository;

import ru.otus.domain.Comment;

import java.util.Optional;

public interface CommentRepositoryJpa {
    Comment save(Comment comment);
    void deleteById(long id);
    Optional<Comment> getById(long id);
    void update(Comment comment);
}