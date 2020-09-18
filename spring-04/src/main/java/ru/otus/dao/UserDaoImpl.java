package ru.otus.dao;

import org.springframework.stereotype.Component;
import ru.otus.domain.User;

import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    @Override
    public Optional<User> findByNameAndSurname(String name, String surname) {
        return Optional.of(new User(name, surname, 29));
    }
}