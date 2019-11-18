package start;

import login.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("login.rest")
@SpringBootApplication
public class Main {
     static UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
