package df.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import svit.io.ClasspathResourceLoader;
import svit.io.Resource;

import java.util.Collection;

import static org.springframework.boot.SpringApplication.run;

@EnableJpaRepositories
@SpringBootApplication
public class DFWebApplication {

    public static void main(String... arguments) {
        Collection<Resource> cpr = new ClasspathResourceLoader().loadResources("classpath:.");

        System.out.println(cpr.size());
//        ApplicationContext applicationContext = run(DFWebApplication.class, arguments);
//        System.out.println(applicationContext);
    }

}
