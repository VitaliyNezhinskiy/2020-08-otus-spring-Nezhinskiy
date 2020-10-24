package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {
    Book getBookByTitle(String title);

    List<Book> getAllBooks();
    void takeBookById(long id);

    void takeAllBooks();

    void saveBook(Book book);
}