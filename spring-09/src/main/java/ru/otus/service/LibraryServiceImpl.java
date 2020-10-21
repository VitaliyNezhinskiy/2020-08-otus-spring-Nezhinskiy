package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.Collections;
import java.util.Optional;


@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private static final String BOOK_NOT_FIND = "Такой книги не найдено";
    private final BookService bookService;
    private final BookPrinterService bookPrinterService;
    private final CommentService commentService;
    private final IOService ioService;

    @Override
    public void start() {
        System.out.println("Добро пожаловать в самую огромную библиотеку мира!");
        printBook();
    }

    @Override
    public void printBook() {
        System.out.println("Введите название книги, которую хотите найти:");
        bookPrinterService.printBookByTitle(ioService.getMessage());
    }

    @Override
    public void printAllBooks() {
        System.out.println("Вот все книги, которые у нас есть:");
        bookPrinterService.printAllBooks();
    }

    @Override
    public void takeBook() {
        System.out.println("Введите id книги, которую хотите забрать:");
        bookService.takeBookById(ioService.getNumMessage());
    }

    @Override
    public void takeAllBooks() {
        System.out.println("Ты молодец, что хочешь забрать все книги! " +
                "\nНе забудь вернуть их все завтра до открытия библиотеки");
        bookService.takeAllBooks();
    }

    @Override
    public void putBook() {
        System.out.println("Введите название книги");
        final String title = ioService.getMessage();
        System.out.println("Введите автора книги");
        final String authorName = ioService.getMessage();
        System.out.println("Введите жанр книги");
        final String genreName = ioService.getMessage();

        bookService.saveBook(new Book(0L, title,
                Collections.singletonList(new Author(0L, authorName)),
                new Genre(0L, genreName),
                Collections.emptyList()));
    }

    @Override
    public void leaveComment() {
        System.out.println("Введите название книги к которой вы " +
                "хотите оставить комментарий");
        final String title = ioService.getMessage();
        Optional<Book> book = bookService.getBookByTitle(title);
        if (book.isPresent()) {
            System.out.println("Введите имя: ");
            String nickname = ioService.getMessage();
            System.out.println("Введите комментарий: ");
            String message = ioService.getMessage();
            commentService.leaveComment(book.get(), nickname, message);
            System.out.println("Спасибо за ваш комментарий." +
                    " Вы помогаете нам стать лучше!");
        } else {
            System.out.println(BOOK_NOT_FIND);
        }
    }

    @Override
    public void deleteComment() {
        System.out.println("Введите название книги у которой вы хотите " +
                "удалить комментарий");
        String tittle = ioService.getMessage();
        final Optional<Book> bookOptional = bookService.getBookByTitle(tittle);

        if (bookOptional.isPresent()) {
            System.out.println("Введите свое имя");
            String nickname = ioService.getMessage();

            bookOptional.get().getComments().stream()
                    .filter(comment -> comment.getNickname().equals(nickname))
                    .forEach(commentService::deleteComment);

            System.out.println(nickname + " ваши комментарии к книге: '"
                    + tittle + "' успешно удалены!");
        } else {
            System.out.println(BOOK_NOT_FIND);
        }
    }

    @Override
    public void allCommentsByTitle() {
        System.out.println("Введите название книги к которой вы " +
                "хотите увидеть все комментарии");
        final String title = ioService.getMessage();
        bookPrinterService.printAllCommentsByTitle(title);
    }
}