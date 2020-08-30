package service;


import dao.UserDao;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import service.exception.UserNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс UserService: ")
public class UserServiceImplTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImpl(userDao, Mockito.mock(IOService.class));
    }

    @DisplayName(" корректно находит пользователя по имени")
    @Test
    public void shouldHaveFindUserByName() {
        User user = new User("V", "V", 1);

        given(userDao.findByName("V"))
                .willReturn(Optional.of(user));

        assertEquals(userService.findByName("V"), user);
    }

    @DisplayName(" кидает исключение, если пользователя не нашли")
    @Test
    public void shouldHaveThrowExceptionWhenUserDontFindByName() {
        given(userDao.findByName(""))
                .willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findByName(""));
    }
}