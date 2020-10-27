package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa extends CrudRepository<Book, Long>, BookRepositoryCustom {
    Book save(Book book);

    Optional<Book> getById(long id);

    List<Book> findAll();

    List<Book> getByTitle(String title);

    void deleteById(long id);
}