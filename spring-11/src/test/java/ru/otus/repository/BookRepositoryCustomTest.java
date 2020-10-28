package ru.otus.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс BookRepositoryCustom должен:")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryCustomTest {
    @Autowired
    private BookRepositoryCustomImpl bookRepositoryCustom;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @DisplayName(" корректно удалять книгу с комментариями")
    @Test
    public void shouldHaveCorrectDeleteBookAndComments() {
        bookRepositoryJpa.findById(2L);
        bookRepositoryCustom.deleteBookAndCommentsById(2L);

        assertAll(() -> assertEquals(Optional.empty(), commentRepositoryJpa.findById(3L)),
                () -> assertEquals(Optional.empty(), commentRepositoryJpa.findById(2L)),
                () -> assertEquals(Optional.empty(), bookRepositoryJpa.findById(2L)));
    }

}
