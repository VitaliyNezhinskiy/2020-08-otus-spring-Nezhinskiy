package ru.otus.service;

import ru.otus.domain.Book;

public interface BookPrinterService {
    void printBook(Book book);

    void printBookByTitle(String title);

    void printAllBooks();

    void printAllComments(Book book);
}
