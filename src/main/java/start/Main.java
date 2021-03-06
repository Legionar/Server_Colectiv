package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan({"login.rest","profile.rest"})
@EnableJpaRepositories({"login.repository","profile.repository"})
@EntityScan({"login.entity","profile.entity"})
@CrossOrigin("*")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
