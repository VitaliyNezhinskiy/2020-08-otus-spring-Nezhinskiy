package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Book;

import java.util.List;

public interface BookRepositoryJpa extends JpaRepository<Book, Long> {
    List<Book> getAllByTitle(String title);
}