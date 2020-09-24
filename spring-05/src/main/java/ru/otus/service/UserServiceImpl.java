package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.UserDao;
import ru.otus.domain.User;
import ru.otus.service.csv.CSVService;
import ru.otus.service.exception.UserNotFoundException;
import ru.otus.utils.MessageSourceWrapper;

import java.util.Optional;
import java.util.stream.IntStream;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final IOService ioService;
    private final CSVService csvService;
    private final MessageSourceWrapper msw;

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
        String name = ioService.writeAndRead(msw.getMessage("hello") + msw.getMessage("name"));

        String surname = ioService.writeAndRead(msw.getMessage("surname"));

        findByNameAndSurname(name, surname);
        ioService.writeMessage(msw.getMessage("wish"));
        ioService.writeMessage(msw.getMessage("answers", name, surname));

        int[] answers = askQuestions(5);
        ioService.writeMessage(msw.getMessage("result",
               String.valueOf(csvService.getPercentOfRightAnswers(answers))));
    }

    private int[] askQuestions(int count) {
        int[] answers = new int[count];
        IntStream.range(0, answers.length).
                forEach(i -> {
            ioService.writeMessage(csvService.getQuestion(i) + "\n"
                    + csvService.getAnswers(i));
            answers[i] = Integer.parseInt(ioService.readMessage());
        });
        return answers;
    }
}