package io.startform.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.startform.parent.jpa")
public class StartFormApiApplication {

    public static void main(String... arguments) {
        run(StartFormApiApplication.class, arguments);
    }

}
