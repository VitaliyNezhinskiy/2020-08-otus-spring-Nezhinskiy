package ru.otus.dao;


import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> getById(long id);
    long insert(Book book);
    int countBooksByTitle(String title);
    void deleteById(long id);
    List<Book> getAll();
    int count();
}
