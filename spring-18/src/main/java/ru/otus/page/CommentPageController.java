package ru.otus.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CommentPageController {

    @GetMapping("/books/{id}/comments")
    public String listPage() {
        return "comments-list";
    }

    @GetMapping("/books/{id}/comments/add")
    public String addPage() {
        return "comments-add";
    }
}
