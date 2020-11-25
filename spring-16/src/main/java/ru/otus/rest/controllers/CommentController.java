package ru.otus.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Book;
import ru.otus.rest.dto.BookDto;
import ru.otus.rest.dto.CommentDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/books/{id}/comments")
    public String listPage(@PathVariable("id") String bookId,
                           Model model) {
        Book book = bookService
                .getBookById(bookId);
        List<CommentDto> comments = book.getComments().stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("bookDto", BookDto.toDto(book));
        model.addAttribute("comments", comments);
        return "comments-list";
    }

    @GetMapping("/books/{id}/comments/add")
    public String addPage(@PathVariable("id") String bookId, Model model) {
        BookDto bookDto = BookDto.toDto(bookService.getBookById(bookId));
        model.addAttribute("bookDto", bookDto);
        return "comments-add";
    }

    @PostMapping("/books/{id}/comments/add")
    public String addPage(@RequestParam("nickname") String nickname,
                          @RequestParam("message") String message,
                          @PathVariable("id") String bookId
    ) {
        commentService.leaveComment(bookService.getBookById(bookId),
                nickname, message);
        return "redirect:/books/{id}/comments";
    }

    @GetMapping("/books/{id}/comments/delete")
    public String deletePage(@PathVariable("id") String id, Model model) {
        BookDto dto = BookDto.toDto(bookService.getBookById(id));
        model.addAttribute("bookDto", dto);
        return "comments-delete";
    }

    @PostMapping("/books/{id}/comments/delete")
    public String deletePage(@RequestParam("nickname") String nickname,
                             @PathVariable("id") String bookId) {

        commentService.deleteCommentByNickname(bookService
                .getBookById(bookId), nickname);

        return "redirect:/books/{id}/comments";
    }
}