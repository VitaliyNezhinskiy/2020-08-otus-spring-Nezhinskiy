package ru.otus.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class BookPagesController {

    @GetMapping("/books")
    public String listPage(Model model) {
        model.addAttribute("keywords", "list books");
        return "books-list";
    }

    @GetMapping("/books/edit")
    public String editPage() {
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

}
