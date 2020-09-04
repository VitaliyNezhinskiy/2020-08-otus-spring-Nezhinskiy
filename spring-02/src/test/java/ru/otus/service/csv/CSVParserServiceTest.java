package ru.otus.service.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Класс CSVParserService:")
public class CSVParserServiceTest {

    @DisplayName("корректно создается конструктором")
    @Test
    public void correctCreateByConstructor() {
        CSVParserService csvParserService = new CSVParserService(this.getClass()
                .getResourceAsStream("/test_questions_with_answers.csv"));
        Assertions.assertFalse(csvParserService.getCsvRecordList().isEmpty());
    }
}