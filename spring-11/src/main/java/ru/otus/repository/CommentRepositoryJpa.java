package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Comment;


public interface CommentRepositoryJpa extends CrudRepository<Comment, Long> {
    void deleteAllByBookId(long bookId);
}