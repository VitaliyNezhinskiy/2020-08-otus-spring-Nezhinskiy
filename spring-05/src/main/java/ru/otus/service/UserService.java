package ru.otus.service;

import ru.otus.domain.User;

public interface UserService {
    User findByNameAndSurname(String name, String surname);

    void startQuiz();
}