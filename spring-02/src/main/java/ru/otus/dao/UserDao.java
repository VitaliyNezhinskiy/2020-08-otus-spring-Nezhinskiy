package ru.otus.dao;

import ru.otus.domain.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByNameAndSurname(String name, String surname);
}