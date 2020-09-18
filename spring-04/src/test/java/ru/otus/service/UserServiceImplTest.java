package ru.otus.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.dao.UserDao;
import ru.otus.domain.User;
import ru.otus.service.csv.CSVService;
import ru.otus.service.exception.UserNotFoundException;
import ru.otus.utils.MessageSourceWrapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс UserService: ")
@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    private UserDao userDao;
    @MockBean
    private IOService ioService;
    @MockBean
    private CSVService csvService;
    @MockBean
    private MessageSourceWrapper msw;

    @Autowired
    private User user;

    private UserService userService;

    @Configuration
    static class Conf{
        @Bean
        User getUser(){
           return new User("N", "S", 1);
        }
    }

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userDao, ioService, csvService, msw);
    }

    @DisplayName(" корректно находит пользователя по имени")
    @Test
    public void shouldHaveFindUserByName() {

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