package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpaImpl.class)
public class GenreRepositoryJpaImplTest {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    GenreRepositoryJpa genreRepositoryJpa;

    @Test
    @DisplayName(" должен находить корректный жанр по имени")
    public void shouldFindCorrectGetByName() {
        val genre = entityManager.find(Genre.class, 1L);

        Optional<Genre> optionalGenre = genreRepositoryJpa.getByName(genre.getName());
        assertThat(optionalGenre)
                .matches(Optional::isPresent)
                .matches(genre1 -> genre1.get().getName().equals(genre.getName()));
    }

    @DisplayName(" должен возврашать корректное количество жанров со всей информацией")
    @Test
    void shouldReturnCorrectAllBooksWithAllInfo() {
        val books = genreRepositoryJpa.getAll();

        assertThat(books).isNotNull().hasSize(2)
                .allMatch(book -> !book.getName().equals(""));
    }
}
