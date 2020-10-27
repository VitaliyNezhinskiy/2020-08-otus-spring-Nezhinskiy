package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;

import java.util.Optional;

public interface CommentRepositoryJpa extends CrudRepository<Comment, Long> {
    void deleteAllByBookId(long bookId);

    void deleteById(long id);

    Optional<Comment> getById(long id);

}