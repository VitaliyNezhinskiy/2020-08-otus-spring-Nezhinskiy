package ru.otus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Класс User: ")
public class UserTest {
    private final static String NAME = "Dimitry";
    private final static String SURNAME = "Nagiev";
    private final static int AGE = 15;
    private final static int CHANGED_AGE = 27;

    @DisplayName(" корректно создает пользователя через конструктор")
    @Test
    public void shouldHaveCorrectConstructor() {
        User actualUser = new User(NAME, SURNAME, AGE);

        assertAll("actualUser",
                () -> assertEquals(NAME, actualUser.getName()),
                () -> assertEquals(SURNAME, actualUser.getSurname()));
    }

    @DisplayName(" корректно меняет возраст у пользователя")
    @Test
    public void shouldHaveCorrectSetAge() {
        User actualUser = new User(NAME, SURNAME, AGE);
        actualUser.setAge(CHANGED_AGE);

        assertEquals(actualUser.getAge(), CHANGED_AGE);
    }

}