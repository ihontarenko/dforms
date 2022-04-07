package io.startform.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.startform.parent.jpa")
public class StartApplication {

    public static void main(String... arguments) {
        SpringApplication.run(StartApplication.class, arguments);
    }

}
