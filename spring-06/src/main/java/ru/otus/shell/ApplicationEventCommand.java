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
    public void startCommand(){
        libraryService.start();
    }

    @ShellMethod(value = "Get all books", key = "greed")
    public void booksGreedCommand(){
        libraryService.takeAwayAllBooks();
    }

}