package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookService bookService;
    private final IOService ioService;

    @Override
    public void takeAwayAllBooks() {
        System.out.println("Ты молодец, что хочешь забрать все книги! " +
                "\nНе забудь вернуть их все завтра до открытия библиотеки");
        bookService.getAll().forEach(System.out::println);
    }

    public void start() {
        System.out.println("Добро пожаловать в самую огромную библеотеку мира!\n" +
                "Введи порядковый номер книги, которую ты ищешь: ");
        long num = Long.parseLong(ioService.getMessage());

        bookService.getById(num).ifPresentOrElse(book ->
                System.out.println("Мы нашли книгу специально для тебя: "
                        + book.getAuthor().getFio()
                        + ": " + book.getTittle()),
                () -> System.out.println("Такой книги нет"));
    }
}