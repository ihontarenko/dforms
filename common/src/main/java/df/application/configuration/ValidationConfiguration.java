package df.application.configuration;

import df.application.validation.custom.FieldValidation;
import df.application.validation.custom.constraint.FieldUsageTypeValidator;
import org.jmouse.validator.old.Validation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfiguration {

    @Bean("fieldValidation")
    public Validation fieldValidation(ApplicationContext context) {
        Validation validation = new FieldValidation("FIELD_VALIDATION", context);

        validation.configure(v
                -> v.addValidator(new FieldUsageTypeValidator()));

        return validation;
    }

}
