package ru.otus.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

@Service
@Getter
public class QuestionService{
    private final BufferedReader reader;

    public QuestionService(@Value("${questions.path}") String sPath) {
        this.reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(sPath))));
    }
}