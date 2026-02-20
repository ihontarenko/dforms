package df.application.validation.custom;

import org.jmouse.validator.old.Validation;
import org.springframework.context.ApplicationContext;

public class FieldValidation extends Validation {

    public FieldValidation(String name, ApplicationContext context) {
        super(name);
    }

}
