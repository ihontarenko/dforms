package df.base.configs;

import df.base.common.validation.custom.MessageResolver;
import df.base.common.validation.custom.Validation;
import df.base.validation.custom.FieldValidation;
import df.base.validation.custom.constraint.FieldUsageTypeValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfiguration {

    @Bean("fieldValidation")
    public Validation fieldValidation(MessageResolver resolver, ApplicationContext context) {
        Validation validation = new FieldValidation("FIELD_VALIDATION", resolver, context);

        validation.configure(v
                -> v.addValidator(new FieldUsageTypeValidator()));

        return validation;
    }

}
