package df.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import svit.ast.recognizer.EnumTokenRecognizer;
import org.jmouse.core.io.CompositeResourceLoader;
import org.jmouse.core.io.Resource;
import org.jmouse.core.reflection.JavaType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableJpaRepositories
@SpringBootApplication
public class DFWebApplication {

    public static void main(String... arguments) {
//        Collection<Resource> cpr = new CompositeResourceLoader().findResources("jrt:java.base/**/*Calendar.class");
//        Collection<Resource> cpr = new CompositeResourceLoader().findResources("local:**/*Loader.class");
//
//        for (Resource resource : cpr) {
//            System.out.println(resource);
//        }
//
//        System.out.println(cpr.size());
//
//
//
//         SpringApplication.run(DFWebApplication.class, arguments);

        Map<Object, Object> beans = new HashMap<>();

        beans.put("list", List.of(1, 2, 3));

        ResolvableType resolvableType = ResolvableType.forInstance(beans.get("list"));
    }

    public static void test(EnumTokenRecognizer<?> test) {
        JavaType.forInstance(test);
    }

}
