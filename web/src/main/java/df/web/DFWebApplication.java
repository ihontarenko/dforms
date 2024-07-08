package df.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableJpaRepositories
public class DFWebApplication {

    public static void main(String... arguments) {
        run(DFWebApplication.class, arguments);
    }

}
