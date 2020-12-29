package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.domain.Author;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий по работе с авторами должен:")
@DataMongoTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName(" корректно находить автора по имени")
    public void shouldFindBookById() {
        authorRepository.save(Author.builder()
                .id("authorId").fio("FIO").build()).block();

        StepVerifier.create(authorRepository.findByFio("FIO"))
                .assertNext(author -> assertAll("Should find author by fio",
                        () -> assertNotNull(author.getId()),
                        () -> assertEquals("authorId", author.getId()),
                        () -> assertNotNull(author.getFio()),
                        () -> assertEquals("FIO", author.getFio())))
                .expectComplete()
                .verify();
    }
}
