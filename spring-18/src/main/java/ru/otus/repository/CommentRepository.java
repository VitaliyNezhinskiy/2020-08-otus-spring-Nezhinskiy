package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Comment;


@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    void deleteAllByBookId(String bookId);
    Flux<Comment> findAllByBookId(String bookId);
    Mono<Comment> save(Mono<Comment> person);
}