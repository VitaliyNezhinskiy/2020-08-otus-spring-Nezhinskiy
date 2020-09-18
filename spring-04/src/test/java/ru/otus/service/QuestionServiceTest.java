package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.utils.MessageSourceWrapper;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuestionService:")
@SpringBootTest
public class QuestionServiceTest {
    @MockBean
    private MessageSourceWrapper msw;

    @Configuration
    static class Conf{}

    @Test
    @DisplayName(" корректно создавается конструктором")
    public void shouldHaveCorrectConstructor() throws IOException {

        Mockito.when(msw.getMessage(QuestionService.PATH)).thenReturn("test_questions_with_answers.csv");

        QuestionService questionService = new QuestionService(msw);

        BufferedReader readerFromQuestionFile = questionService.getReader();
        assertNotNull(readerFromQuestionFile.readLine());
        assertNull(readerFromQuestionFile.readLine(), "В файле появилась еще одна строка");
    }
}
