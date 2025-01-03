package df.application.validation.custom;

import df.common.validation.custom.Validation;
import org.springframework.context.ApplicationContext;

public class FieldValidation extends Validation {

    public FieldValidation(String name, ApplicationContext context) {
        super(name, context);
    }

}
