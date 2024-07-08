package df.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "df.parent.jpa")
public class DFAPIApplication {

    public static void main(String... arguments) {
        run(DFAPIApplication.class, arguments);
    }

}
