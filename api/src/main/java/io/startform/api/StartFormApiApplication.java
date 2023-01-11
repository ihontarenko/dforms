package io.startform.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.startform.parent.jpa")
public class StartFormApiApplication {

    public static void main(String... arguments) {
        SpringApplication.run(StartFormApiApplication.class, arguments);
    }

}
