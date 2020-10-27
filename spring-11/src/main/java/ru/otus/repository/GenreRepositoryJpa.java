package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepositoryJpa extends CrudRepository<Genre, Long> {
    Optional<Genre> getByName(String name);
    List<Genre> findAll();
}
