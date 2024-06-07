package pro.javadev.test.sub.application2;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan(basePackageClasses = ConfigB.class)
public class ConfigB {

    @Bean
    public String string() {
        return getClass().getName();
    }

}
