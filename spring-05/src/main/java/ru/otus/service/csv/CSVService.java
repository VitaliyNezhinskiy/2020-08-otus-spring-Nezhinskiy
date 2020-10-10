package ru.otus.service.csv;

public interface CSVService {
    String getQuestion(int number);

    String getAnswers(int number);

    int getRightAnswer(int number);

    double getPercentOfRightAnswers(int[] answers);
}
