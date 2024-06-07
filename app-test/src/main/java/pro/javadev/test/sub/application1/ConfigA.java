package pro.javadev.test.sub.application1;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan(basePackageClasses = ConfigA.class)
public class ConfigA {

    @Bean
    public String string() {
        return getClass().getName();
    }

}
