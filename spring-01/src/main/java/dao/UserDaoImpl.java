package dao;

import domain.User;

import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public Optional<User> findByName(String name) {
        return Optional.of(new User("Vitaliy", "Nezhinskiy", 29));
    }
}