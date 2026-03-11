package df.application.configuration;

import org.jmouse.el.ExpressionLanguage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpressionLanguageConfiguration {

    @Bean
    public ExpressionLanguage expressionLanguage() {
        return new ExpressionLanguage();
    }

}
