package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.service.UserService;
import ru.otus.config.YamlProps;

@EnableConfigurationProperties(YamlProps.class)
@SpringBootApplication
public class MyApp {

    public static void main(String[] args) {
        var context =  SpringApplication.run(MyApp.class);
        context.getBean(UserService.class).startQuiz();
    }
}