package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import ru.otus.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;


@ComponentScan({"ru.otus.service", "ru.otus.dao", "ru.otus.service.csv"})
@PropertySource("classpath:application.properties")
public class MyApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyApp.class);
        context.getBean(UserService.class).startQuiz();
    }

    @Bean("questionsStream")
    public InputStream getStreamFromCSVFile(@Value("${questions.path}") String sPath) {
        return getClass().getClassLoader().getResourceAsStream(sPath);
    }
}