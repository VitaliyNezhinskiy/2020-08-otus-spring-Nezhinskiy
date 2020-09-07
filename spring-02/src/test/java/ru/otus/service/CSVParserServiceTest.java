package ru.otus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.service.csv.CSVParserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.mockito.Mockito.when;


@DisplayName("Класс CSVParserService:")
public class CSVParserServiceTest {

    @DisplayName("корректно создается конструктором")
    @Test
    public void correctCreateByConstructor() {
        QuestionService questionService = Mockito.mock(QuestionService.class);

        when(questionService.getReader()).thenReturn(new BufferedReader
                (new InputStreamReader(
                        this.getClass()
                                .getResourceAsStream("/test_questions_with_answers.csv"))));

        CSVParserService csvParserService = new CSVParserService(questionService);
        Assertions.assertFalse(csvParserService.getCsvRecordList().isEmpty());
    }
}