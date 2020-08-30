package service;

import domain.User;

public interface UserService {
    User findByName(String name);

    void startQuiz();
}