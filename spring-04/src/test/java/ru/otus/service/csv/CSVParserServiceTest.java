package ru.otus.service.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.QuestionService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


@DisplayName("Класс CSVParserService:")
@SpringBootTest
public class CSVParserServiceTest {

    @Autowired
    private CSVParserService csvParserService;

    @Configuration
    static class Conf{

        @Bean
        CSVParserService csvParserService(){
            QuestionService questionService = Mockito.mock(QuestionService.class);
            Mockito.when(questionService.getReader()).thenReturn(new BufferedReader
                    (new InputStreamReader(new ByteArrayInputStream(
                            ("It has got one color for winter and summer. " +
                                    "What is it?,fir,beam,lightbulb,hare,1").getBytes(Charset.defaultCharset())))));

            return new CSVParserService(questionService);
        }
    }

    @DisplayName("корректно создается конструктором")
    @Test
    public void correctCreateByConstructor() {
        Assertions.assertFalse(csvParserService.getCsvRecordList().isEmpty());
    }
}