package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Comment;

import java.util.List;


public interface CommentRepositoryJpa extends MongoRepository<Comment, String> {
    void deleteAllByBookId(String bookId);
    List<Comment> findAllByBookId(String bookId);
}