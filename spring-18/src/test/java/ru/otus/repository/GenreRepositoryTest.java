package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий по работе с жанрами должен:")
@DataMongoTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName(" корректно находить жанр по имени")
    public void shouldFindBookById() {
        genreRepository.save(Genre.builder()
                .id("genreId").name("NAME").build()).block();

        StepVerifier.create(genreRepository.findByName("NAME"))
                .assertNext(genre -> assertAll("Should find genre by name",
                        () -> assertNotNull(genre.getId()),
                        () -> assertEquals("genreId", genre.getId()),
                        () -> assertNotNull(genre.getName()),
                        () -> assertEquals("NAME", genre.getName())))
                .expectComplete()
                .verify();
    }
}
