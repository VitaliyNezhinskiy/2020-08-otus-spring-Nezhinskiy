package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {
    Book getBookByTitle(String title);

    List<Book> getAllBooks();
    void takeBookById(String id);

    void takeAllBooks();

    void saveBook(Book book);
}