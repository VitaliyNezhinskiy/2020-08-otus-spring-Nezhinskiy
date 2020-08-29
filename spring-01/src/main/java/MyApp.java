import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserService;

public class MyApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(UserService.class).startQuiz();
    }
}