package dao;

import domain.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByName(String name);
}