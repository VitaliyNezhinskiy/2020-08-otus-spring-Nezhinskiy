package ru.otus.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.rest.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getALL() {
        return bookService.getAllBooks()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/api/books/{id}")
    public BookDto editPage(@PathVariable("id") String id) {
        return BookDto.toDto(bookService.getBookById(id));
    }

    @PostMapping("/api/books/{id}")
    public String editBook(BookDto bookDto) {
        bookService.saveBook(BookDto.toDomainObject(bookDto));
        return "redirect:/";
    }

    @PutMapping("/api/books/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        bookService.saveBook(BookDto.toDomainObject(bookDto));
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/api/books/{id}")
    public String deletePage(@PathVariable("id") String id) {
        bookService.takeBookById(id);
        return "redirect:/";
    }

    @PostMapping("/api/books/add")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        bookService.saveBook(BookDto.toDomainObject(bookDto));
        return ResponseEntity.ok(bookDto);
    }
}