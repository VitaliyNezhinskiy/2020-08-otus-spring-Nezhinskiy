package ru.otus.service.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.service.QuestionService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static org.mockito.Mockito.when;


@DisplayName("Класс CSVParserService:")
public class CSVParserServiceTest {

    @DisplayName("корректно создается конструктором")
    @Test
    public void correctCreateByConstructor() {
        QuestionService questionService = Mockito.mock(QuestionService.class);

        when(questionService.getReader()).thenReturn(new BufferedReader
                (new InputStreamReader(new ByteArrayInputStream(
                        ("It has got one color for winter and summer. " +
                                "What is it?,fir,beam,lightbulb,hare,1").getBytes(Charset.defaultCharset())))));

        CSVParserService csvParserService = new CSVParserService(questionService);
        Assertions.assertFalse(csvParserService.getCsvRecordList().isEmpty());
    }
}