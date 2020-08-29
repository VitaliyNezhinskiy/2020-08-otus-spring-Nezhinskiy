package service;

import dao.UserDao;
import domain.User;
import service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static utils.Utils.*;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final IOService ioService;

    public User findByName(String name) {
        Optional<User> optionalUser = userDao.findByName(name);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException();
    }

    @Override
    public void startQuiz() {
        ioService.writeMessage("Hello, my friend. \nPls write down your name:");
        String name = ioService.readMessage();

        User user = findByName(name);
        ioService.writeMessage("Good day for " + user.getName() + " " + user.getSurname());

        int[] answers = askQuestions(5);
        ioService.writeMessage("Your percents of correct answers are " + getPercentOfRightAnswers(answers));
    }

    public int[] askQuestions(int count) {
        int[] answers = new int[count];
        for (int i = 0; i < answers.length; i++) {
            ioService.writeMessage(getQuestion(i) + "\n" + getAnswers(i));
            answers[i] = Integer.parseInt(ioService.readMessage());
        }
        return answers;
    }
}