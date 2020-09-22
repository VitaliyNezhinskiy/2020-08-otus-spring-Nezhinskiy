package ru.otus.service.csv;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@AllArgsConstructor
@Service
public class CSVService {
    private final CSVParserService csvParserService;

    public String getQuestion(int number) {
        return csvParserService.getCsvRecordList().get(number).get(Header.QUESTION);
    }

    public String getAnswers(int number) {
        CSVRecord csvRecord = csvParserService.getCsvRecordList().get(number);
        return " 1." + csvRecord.get(Header.ANSWER1) + " 2." + csvRecord.get(Header.ANSWER2) + " 3."
                + csvRecord.get(Header.ANSWER3) + " 4." + csvRecord.get(Header.ANSWER4);
    }

    public int getRightAnswer(int number) {
        return Integer.parseInt(csvParserService.getCsvRecordList().get(number).get(Header.RIGHT_ANSWER));
    }

    public double getPercentOfRightAnswers(int[] answers) {
        if (answers.length == csvParserService.getCsvRecordList().size()) {
            int numOfRightAnswers = (int) IntStream.range(0, answers.length)
                    .filter(i -> answers[i] == getRightAnswer(i)).count();

            return (100.0 * numOfRightAnswers / answers.length);
        } else throw new RuntimeException("В массиве содержатся не все ответы на вопросы");
    }
}
