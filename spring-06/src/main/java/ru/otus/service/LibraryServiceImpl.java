package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;


@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookDao bookDao;
    private final IOService ioService;

    @Override
    public void takeAwayAllBooks() {
        System.out.println("Ты молодец, что хочешь забрать все книги! " +
                "\nНе забудь вернуть их все завтра до открытия библиотеки");
        bookDao.getAll().forEach(System.out::println);
    }

    public void start() {
        System.out.println("Добро пожаловать в самую огромную библеотеку мира!\n" +
                "Введи порядковый номер книги, которую ты ищешь: ");
        long num = Long.parseLong(ioService.getMessage());

        bookDao.getById(num).ifPresentOrElse(book ->
                System.out.println("Мы нашли книгу специально для тебя: "
                        + book.getAuthor().getFio()
                        + ": " + book.getTittle()),
                () -> System.out.println("Такой книги нет"));
    }
}