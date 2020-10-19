package ru.otus.service;

public interface LibraryService {
    void start();

    void printBook();

    void printAllBooks();

    void putBook();

    void takeBook();

    void takeAllBooks();

    void leaveComment();

    void deleteComment();
}
