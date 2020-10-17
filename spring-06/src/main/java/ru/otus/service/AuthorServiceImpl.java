package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public Optional<Author> getById(long id) {
        try {
            return authorDao.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public long insert(Author author) {
        final String authorFio = author.getFio();

        Optional<Author> authorOptional = findByFio(authorFio);
        return authorOptional.map(Author::getId).orElseGet(() -> authorDao.insert(author));
    }

    @Override
    public int countAuthors() {
        return authorDao.countAuthors();
    }

    @Override
    public Optional<Author> findByFio(String fio) {
        try {
            return authorDao.findByFio(fio);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}