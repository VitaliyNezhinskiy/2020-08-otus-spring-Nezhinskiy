package ru.otus.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.rest.dto.BookDto;
import ru.otus.service.BookService;


@AllArgsConstructor
@Controller
public class BookPagesController {
    private final BookService bookService;

    @GetMapping("/books")
    public String listPage(Model model) {
        model.addAttribute("keywords", "list books");
        return "books-list";
    }

    @GetMapping("/books/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        BookDto dto = BookDto.toDto(bookService.getBookById(id));
        model.addAttribute("bookDto", dto);
        return "books-edit";
    }

    @GetMapping("/books/add")
    public String addPage() {
        return "books-add";
    }

    @GetMapping("/books/delete")
    public String deletePage() {
        return "books-delete";
    }


    @GetMapping("/books/comments")
    public String commentPage() {
        return "comments-list";
    }
}
