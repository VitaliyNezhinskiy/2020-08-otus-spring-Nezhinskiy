package ru.otus;

import org.springframework.context.annotation.PropertySource;
import ru.otus.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"ru.otus.service", "ru.otus.dao", "ru.otus.service.csv"})
@PropertySource("classpath:application.properties")
public class MyApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyApp.class);
        context.getBean(UserService.class).startQuiz();
    }
}