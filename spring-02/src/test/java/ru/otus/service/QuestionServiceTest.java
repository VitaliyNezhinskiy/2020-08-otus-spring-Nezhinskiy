package ru.otus.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс CSVParserService:")
public class QuestionServiceTest {

    @Test
    @DisplayName(" корректно создавается конструктором")
    public void shouldHaveCorrectConstructor() throws IOException {
        QuestionService questionService = new QuestionService("./test_questions_with_answers.csv");
        BufferedReader readerFromQuestionFile = questionService.getReader();
        assertNotNull(readerFromQuestionFile.readLine());
        assertNull(readerFromQuestionFile.readLine(), "В файле появилась еще одна строка");
    }
}
