package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getByFio(String fio) {
        try {
            return authorRepository.getByFio(fio);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
