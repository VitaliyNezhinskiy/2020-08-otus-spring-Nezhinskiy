package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Utils {
    private static List<CSVRecord> csvRecordList;

    public Utils(String sPath) throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().
                getClassLoader().getResource(sPath)).toURI());

        try (BufferedReader reader = Files.newBufferedReader(path);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withHeader(Header.class))) {

            csvRecordList = csvParser.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getQuestion(int number) {
        return csvRecordList.get(number).get(Header.QUESTION);
    }

    public static String getAnswers(int number) {
        CSVRecord csvRecord = csvRecordList.get(number);
        return " 1." + csvRecord.get(Header.ANSWER1) + " 2." + csvRecord.get(Header.ANSWER2) + " 3."
                + csvRecord.get(Header.ANSWER3) + " 4." + csvRecord.get(Header.ANSWER4);
    }

    public static int getRightAnswer(int number) {
        return Integer.parseInt(csvRecordList.get(number).get(Header.RIGHT_ANSWER));
    }

    public static double getPercentOfRightAnswers(int[] answers) {
        if (answers.length == csvRecordList.size()) {
            int numOfRightAnswers = 0;
            for (int i = 0; i < answers.length; i++) {
                if (answers[i] == getRightAnswer(i)) {
                    numOfRightAnswers++;
                }
            }
            return (100.0 * numOfRightAnswers / answers.length);
        } else throw new RuntimeException("В массиве содержаться не все ответы на вопросы");
    }
}
