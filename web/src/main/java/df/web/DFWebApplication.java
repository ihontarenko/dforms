package df.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;

@EnableJpaRepositories
@SpringBootApplication
public class DFWebApplication {

    public static void main(String... arguments) {
        ApplicationContext applicationContext = run(DFWebApplication.class, arguments);
        System.out.println(applicationContext);
    }

}
