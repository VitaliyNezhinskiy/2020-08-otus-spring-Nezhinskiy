package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {
    Book getBookByTitle(String title);

    Book getBookById(String id);

    List<Book> getAllBooks();
    void takeBookById(String id);
    void takeBookByTitle(String title);
    void takeAllBooks();

    void saveBook(Book book);
}