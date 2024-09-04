package df.api.configuration;

import df.base.configs.CommonConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAutoConfiguration
@ComponentScan("df.api")
@ConfigurationPropertiesScan(basePackages = {"df.api.property"})
@Import(CommonConfiguration.class)
public class ApiConfiguration implements WebMvcConfigurer {

    // @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties =
                new PropertySourcesPlaceholderConfigurer();

        properties.setPlaceholderPrefix("api:");
        properties.setLocations(
                new ClassPathResource("application.yml"),
                new ClassPathResource("api-default.yml")
        );
        properties.setIgnoreResourceNotFound(false);
        System.out.println(properties);
        return properties;
    }

}
