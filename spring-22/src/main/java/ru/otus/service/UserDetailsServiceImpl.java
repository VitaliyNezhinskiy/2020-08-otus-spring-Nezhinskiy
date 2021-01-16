package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;
import ru.otus.repository.UserRepository;

import java.util.Collections;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user1 = userRepository.findAll()
                .stream().findAny()
                .orElseThrow(NoSuchElementException::new);

        return new org.springframework.security.core.userdetails.User(
                user1.getName(),
                user1.getPassword(),
                Collections.emptySet());
    }
}
