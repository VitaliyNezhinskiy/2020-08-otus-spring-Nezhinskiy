package ru.otus.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.LibraryService;

@ShellComponent
@AllArgsConstructor
public class ApplicationEventCommand {
    private final LibraryService libraryService;

    @ShellMethod(value = "Start command", key = "start")
    public void startCommand() {
        libraryService.start();
    }

    @ShellMethod(value = "Print all books", key = "find all")
    public void bookFindAllCommand() {
        libraryService.printAllBooks();
    }

    @ShellMethod(value = "Print one book", key = "find one")
    public void bookFindOneCommand() {
        libraryService.printBook();
    }

    @ShellMethod(value = "Take one book", key = "take one")
    public void bookTakeOneCommand() {
        libraryService.takeBook();
    }

    @ShellMethod(value = "Take all books", key = "take all")
    public void bookTakeAllCommand() {
        libraryService.takeAllBooks();
    }

    @ShellMethod(value = "Give book", key = "give")
    public void bookPutCommand() {
        libraryService.putBook();
    }

    @ShellMethod(value = "Leave comment", key = "comment")
    public void commentCommand() {
        libraryService.leaveComment();
    }

    @ShellMethod(value = "Delete comment", key = "del comment")
    public void deleteCommentCommand() {
        libraryService.deleteComment();
    }

    @ShellMethod(value = "All comment", key = "all comments")
    public void allCommentsCommand() {
        libraryService.allCommentsByTitle();
    }
}