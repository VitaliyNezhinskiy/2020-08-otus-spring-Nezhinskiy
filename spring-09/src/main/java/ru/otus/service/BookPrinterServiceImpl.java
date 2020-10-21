package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookPrinterServiceImpl implements BookPrinterService {
    private final static String BOOK_NOT_FIND = "Такой книги нет";
    private final BookService bookService;

    @Override
    public void printBook(Book book) {
        String authors = book.getAuthors().stream()
                .map(Author::getFio).collect(Collectors.joining(", "));

        System.out.println("Книга: " + book.getTitle() + " (id = " + book.getId()
                + "); Авторы: " + authors + "; Жанр: " + book.getGenre().getName());
    }

    @Transactional(readOnly = true)
    @Override
    public void printBookByTitle(String title) {
        bookService.getBookByTitle(title).
                ifPresentOrElse(this::printBook,
                        () -> System.out.println(BOOK_NOT_FIND));
    }

    @Transactional(readOnly = true)
    @Override
    public void printAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Пусто");
        } else {
            books.forEach(this::printBook);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void printAllCommentsByTitle(String title) {
        bookService.getBookByTitle(title).
                ifPresentOrElse((book) ->System.out.println(book.getComments().stream()
                                .map(comment -> comment.getNickname() + ": " + comment.getMessage())
                                .collect(Collectors.joining(", "))),
                        () -> System.out.println(BOOK_NOT_FIND));
    }
}