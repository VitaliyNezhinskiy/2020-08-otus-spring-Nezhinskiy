package ru.otus.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.UserService;

@ShellComponent
@AllArgsConstructor
public class ApplicationEventCommand {
    private final UserService userService;

    @ShellMethod(value = "Start command", key = {"s", "start"})
    public void start() {
        userService.startQuiz();
    }

    @ShellMethod(value = "check Registration", key = {"reg", "registration"})
    public String isRegistered(String name, @ShellOption(defaultValue = "Фамилия") String surname) {
        return userService.findByNameAndSurname(name, surname) != null
                ? "Пользователь зарегистрирован":"Вы не зарегистрированы";
    }
}
