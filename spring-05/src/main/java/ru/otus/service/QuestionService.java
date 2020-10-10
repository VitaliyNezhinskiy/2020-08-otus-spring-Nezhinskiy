package ru.otus.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.otus.utils.MessageSourceWrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

@Service
@Getter
public class QuestionService {
    private final BufferedReader reader;
    public final static String PATH = "questions.path";

    public QuestionService(MessageSourceWrapper messageSourceWrapper) {

        this.reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream(messageSourceWrapper.getMessage(PATH)))));
    }
}