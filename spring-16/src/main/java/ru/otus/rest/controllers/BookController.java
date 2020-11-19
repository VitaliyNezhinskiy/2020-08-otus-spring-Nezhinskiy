package ru.otus.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.rest.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping({"/", "/books"})
    public String listPage(Model model) {
        List<BookDto> books = bookService.getAllBooks().stream()
                .map(BookDto::toDto).collect(Collectors.toList());
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/books/{id}/edit")
    public String editPage(@PathVariable("id") String id, Model model) {
        BookDto dto = BookDto.toDto(bookService.getBookById(id));
        model.addAttribute("bookDto", dto);
        return "books-edit";
    }

    @PostMapping("/books/{id}/edit")
    public String editPage(BookDto bookDto) {
        bookService.saveBook(BookDto.toDomainObject(bookDto));
        return "redirect:/";
    }

    @GetMapping("books/add")
    public String addPage() {
        return "books-add";
    }

    @PostMapping("books/add")
    public String addPage(BookDto bookDto) {
        bookService.saveBook(BookDto.toDomainObject(bookDto));
        return "redirect:/";
    }

    @GetMapping("books/delete")
    public String deletePage() {
        return "books-delete";
    }

    @PostMapping("books/delete")
    public String deletePage(String title) {
        bookService.takeBookByTitle(title);
        return "redirect:/";
    }
}