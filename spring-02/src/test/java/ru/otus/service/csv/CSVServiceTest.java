package ru.otus.service.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.service.QuestionService;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Класс CSVService: ")
public class CSVServiceTest {
    private final CSVService CSVService = new CSVService(new CSVParserService(
            new QuestionService("/test_questions_with_answers.csv")));


    @Test
    @DisplayName(" корректно получает вопросы по индексу")
    public void shouldCorrectGetQuestionsByNum() {
        assertNotNull(CSVService.getQuestion(0));
        assertThrows(IndexOutOfBoundsException.class, () -> CSVService.getQuestion(1));
    }

    @Test
    @DisplayName(" корректно получает ответы по индексу")
    public void shouldCorrectGetAnswersByNum() {
        assertNotNull(CSVService.getAnswers(0));
        assertThrows(IndexOutOfBoundsException.class, () -> CSVService.getAnswers(1));
    }

    @Test
    @DisplayName(" корректно получает правильный ответ по индексу")
    public void shouldCorrectGetRightAnswerByNum() {
        assertEquals(1, CSVService.getRightAnswer(0));
        assertThrows(IndexOutOfBoundsException.class, () -> CSVService.getRightAnswer(1));
    }

    @Test
    @DisplayName(" корректно получает процент правильных ответов")
    public void shouldCorrectGetPercentOfRightAnswers() {
        assertEquals(100.0, CSVService.getPercentOfRightAnswers(new int[]{1}));
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> CSVService.getPercentOfRightAnswers(new int[]{1, 2}));

        assertTrue(exception.getMessage().contains("В массиве содержатся не все ответы на вопросы"));
    }
}