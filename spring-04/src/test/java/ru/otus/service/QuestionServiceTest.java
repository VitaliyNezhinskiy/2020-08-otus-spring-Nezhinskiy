package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.utils.MessageSourceWrapper;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuestionService:")
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Configuration
    static class Conf {

        @Bean
        QuestionService questionService() {
            MessageSourceWrapper msw = Mockito.mock(MessageSourceWrapper.class);
            Mockito.when(msw.getMessage(QuestionService.PATH)).thenReturn("test_questions_with_answers.csv");
            return new QuestionService(msw);
        }
    }

    @Test
    @DisplayName(" корректно создавается конструктором")
    public void shouldHaveCorrectConstructor() throws IOException {
        BufferedReader readerFromQuestionFile = questionService.getReader();
        assertNotNull(readerFromQuestionFile.readLine());
        assertNull(readerFromQuestionFile.readLine(), "В файле появилась еще одна строка");
    }
}