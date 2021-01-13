package ru.otus.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.rest.dto.BookDto;
import ru.otus.rest.dto.CommentDto;


import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @GetMapping("/flux/books/{id}/comments")
    public Mono<ResponseEntity<List<CommentDto>>> getAllComments(@PathVariable("id") String bookId) {
        return commentRepository.findAllByBookId(bookId)
                .map(CommentDto::toDto)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @PostMapping("/flux/books/{id}/comments")
    public Mono<ResponseEntity<CommentDto>> createComment(@PathVariable("id") String id,
                                                          @RequestBody CommentDto commentDto) {
        Mono<Comment> monoComment = Mono.just(Comment.builder()
                        .bookId(id)
                        .nickname(commentDto.getNickname())
                        .message(commentDto.getMessage()).build());

        return Mono.zip(monoComment, Mono.just(1))
                .flatMap(objects -> commentRepository.save(objects.getT1()))
                .map(CommentDto::toDto)
                .map(ResponseEntity::ok);
    }
}