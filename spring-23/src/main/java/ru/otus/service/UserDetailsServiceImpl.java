package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;
import ru.otus.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User userByName = userRepository.findAll()
                .stream()
                .filter(user -> user.getName().equals(s))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(userByName.getName())
                .password(userByName.getPassword())
                .roles(userByName.getRole())
                .build();
    }
}
