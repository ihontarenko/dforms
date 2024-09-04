package df.base.validation.custom;

import df.base.common.validation.custom.MessageResolver;
import df.base.common.validation.custom.Validation;
import org.springframework.context.ApplicationContext;

public class FieldValidation extends Validation {

    public FieldValidation(String name, MessageResolver resolver, ApplicationContext context) {
        super(name, resolver, context);
    }

}
