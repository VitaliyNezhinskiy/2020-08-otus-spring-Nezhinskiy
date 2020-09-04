package ru.otus.dao;

import ru.otus.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    @Override
    public Optional<User> findByNameAndSurname(String name, String surname) {
        return Optional.of(new User(name, surname, 29));
    }
}