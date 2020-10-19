package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Book save(Book book);
    Optional<Book> getById(long id);

    List<Book> getAll();
    List<Book> getByTitle(String title);

    void updateBook(Book book);
    void deleteById(long id);
}