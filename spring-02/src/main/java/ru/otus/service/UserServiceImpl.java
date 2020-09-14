package ru.otus.service;

import lombok.AllArgsConstructor;
import ru.otus.dao.UserDao;
import ru.otus.domain.User;
import org.springframework.stereotype.Service;
import ru.otus.service.exception.UserNotFoundException;
import ru.otus.service.csv.CSVService;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final IOService ioService;
    private final CSVService CSVService;

    @Override
    public User findByNameAndSurname(String name, String surname) {
        Optional<User> optionalUser = userDao.findByNameAndSurname(name, surname);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException();
    }

    @Override
    public void startQuiz() {
        String name = ioService.writeAndRead("Hello, my friend. \nPlease write down your name:");

        String surname = ioService.writeAndRead("Please write down your surname:");

        User user = findByNameAndSurname(name, surname);
        ioService.writeMessage("Good day for " + user.getName());

        int[] answers = askQuestions(5);
        ioService.writeMessage("Your percents of correct answers are " + CSVService.getPercentOfRightAnswers(answers));
    }

    public int[] askQuestions(int count) {
        int[] answers = new int[count];
        for (int i = 0; i < answers.length; i++) {
            ioService.writeMessage(CSVService.getQuestion(i) + "\n" + CSVService.getAnswers(i));
            answers[i] = Integer.parseInt(ioService.readMessage());
        }
        return answers;
    }
}