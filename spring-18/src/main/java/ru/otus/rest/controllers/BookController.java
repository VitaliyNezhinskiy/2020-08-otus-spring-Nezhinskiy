package ru.otus.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.rest.dto.BookDto;

import java.util.List;

@AllArgsConstructor
@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/flux/books")
    public Mono<ResponseEntity<List<BookDto>>> getALL() {
        return bookRepository.findAll()
                .map(BookDto::toDto)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/flux/books/{id}")
    public Mono<ResponseEntity<BookDto>> editPage(@PathVariable("id") String id) {
        return bookRepository.findById(id).map(BookDto::toDto)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/flux/books/{id}")
    public Mono<ResponseEntity<BookDto>> editBook(BookDto bookDto) {
        return bookRepository.save(BookDto.toDomainObject(bookDto))
                .map(BookDto::toDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/flux/books/{id}")
    public Mono<ResponseEntity<BookDto>> updateBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(BookDto.toDomainObject(bookDto))
                .map(book -> ResponseEntity.ok(bookDto));
    }


    @DeleteMapping("/flux/books/{id}")
    public Mono<ResponseEntity<Void>> deletePage(@PathVariable("id") String id) {
        return bookRepository
                .deleteById(id)
                .map(ResponseEntity::ok);
    }

    @Transactional
    @PostMapping("/flux/books/add")
    public Mono<ResponseEntity<BookDto>> createBook(@Validated @RequestBody BookDto bookDto) {
        Mono<Author> authorMono = authorRepository.findByFio(bookDto.getAuthors())
                .switchIfEmpty(authorRepository
                        .save(Author.builder()
                                .fio(bookDto.getAuthors())
                                .build()));

        Mono<Genre> genreMono = genreRepository.findByName(bookDto.getAuthors())
                .switchIfEmpty(genreRepository
                        .save(Genre.builder()
                                .name(bookDto.getGenre())
                                .build()));

        Mono<Book> bookMono = Mono.zip(authorMono, genreMono)
                .flatMap(objects -> Mono.just(Book.builder()
                        .id(bookDto.getId())
                        .title(bookDto.getTitle())
                        .authors(List.of(objects.getT1()))
                        .genre(objects.getT2())
                        .build()));

        return Mono.zip(bookMono, Mono.just(1))
                .flatMap(objects -> bookRepository.save(objects.getT1()))
                .map(BookDto::toDto)
                .map(ResponseEntity::ok);
    }
}