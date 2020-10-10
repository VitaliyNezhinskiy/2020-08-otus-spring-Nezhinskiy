package ru.otus.service.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Класс CSVService: ")
@SpringBootTest
public class CSVServiceImplTest {

    @Configuration
    static class Conf {
        @Bean
        CSVService csvService() throws IOException {
            CSVParserService csvParserService = Mockito.mock(CSVParserService.class);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getClassLoader()
                            .getResourceAsStream("test_questions_with_answers.csv"))));

            Mockito.when(csvParserService.getCsvRecordList()).thenReturn(
                    new CSVParser(reader, CSVFormat.DEFAULT
                            .withHeader(Header.class)).getRecords()
            );
            return new CSVServiceImpl(csvParserService);
        }
    }

    @Autowired
    private CSVService csvService;

    @Test
    @DisplayName(" корректно получает вопросы по индексу")
    public void shouldCorrectGetQuestionsByNum() {
        assertNotNull(csvService.getQuestion(0));
        assertThrows(IndexOutOfBoundsException.class, () -> csvService.getQuestion(1));
    }

    @Test
    @DisplayName(" корректно получает ответы по индексу")
    public void shouldCorrectGetAnswersByNum() {
        assertNotNull(csvService.getAnswers(0));
        assertThrows(IndexOutOfBoundsException.class, () -> csvService.getAnswers(1));
    }

    @Test
    @DisplayName(" корректно получает правильный ответ по индексу")
    public void shouldCorrectGetRightAnswerByNum() {
        assertEquals(1, csvService.getRightAnswer(0));
        assertThrows(IndexOutOfBoundsException.class, () -> csvService.getRightAnswer(1));
    }

    @Test
    @DisplayName(" корректно получает процент правильных ответов")
    public void shouldCorrectGetPercentOfRightAnswers() {
        assertEquals(100.0, csvService.getPercentOfRightAnswers(new int[]{1}));
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> csvService.getPercentOfRightAnswers(new int[]{1, 2}));

        assertTrue(exception.getMessage().contains("В массиве содержатся не все ответы на вопросы"));
    }
}