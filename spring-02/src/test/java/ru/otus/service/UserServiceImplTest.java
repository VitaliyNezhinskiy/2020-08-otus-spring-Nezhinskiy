package ru.otus.service;


import ru.otus.dao.UserDao;
import ru.otus.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.service.csv.CSVService;
import ru.otus.service.exception.UserNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс UserService: ")
public class UserServiceImplTest {
    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImpl(userDao, Mockito.mock(IOService.class), Mockito.mock(CSVService.class));
    }

    @DisplayName(" корректно находит пользователя по имени")
    @Test
    public void shouldHaveFindUserByName() {
        User user = new User("N", "S", 1);

        given(userDao.findByNameAndSurname("N", "S"))
                .willReturn(Optional.of(user));

        Assertions.assertEquals(userService.findByNameAndSurname("N", "S"), user);
    }

    @DisplayName(" кидает исключение, если пользователя не нашли")
    @Test
    public void shouldHaveThrowExceptionWhenUserDontFindByName() {
        given(userDao.findByNameAndSurname("", ""))
                .willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findByNameAndSurname("", ""));
    }
}