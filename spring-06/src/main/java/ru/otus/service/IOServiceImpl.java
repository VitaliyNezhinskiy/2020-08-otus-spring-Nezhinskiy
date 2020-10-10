package ru.otus.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class IOServiceImpl implements IOService{
    public static final BufferedReader console =
            new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    @Override
    public String getMessage() {
        return console.readLine();
    }
}
