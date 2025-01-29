package df.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import svit.ast.recognizer.EnumTokenRecognizer;
import org.jmouse.core.io.CompositeResourceLoader;
import org.jmouse.core.io.Resource;
import org.jmouse.core.reflection.JavaType;

import java.util.Collection;

@EnableJpaRepositories
@SpringBootApplication
public class DFWebApplication {

    public static void main(String... arguments) {
//        Collection<Resource> cpr = new CompositeResourceLoader().findResources("jrt:java.base/**/*Calendar.class");
        Collection<Resource> cpr = new CompositeResourceLoader().findResources("local:**/*Loader.class");

        for (Resource resource : cpr) {
            System.out.println(resource);
        }

        System.out.println(cpr.size());



         SpringApplication.run(DFWebApplication.class, arguments);
    }

    public static void test(EnumTokenRecognizer<?> test) {
        JavaType.forInstance(test);
    }

}
