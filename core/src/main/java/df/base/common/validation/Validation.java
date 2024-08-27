package df.base.common.validation;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Validation {

    private final AutowireCapableBeanFactory beanFactory;
    private final List<Validator>            validators = new ArrayList<>();
    private final String                     name;
    private final MessageResolver            resolver;

    public Validation(String name, MessageResolver resolver, ApplicationContext context) {
        this.name = name;
        this.resolver = resolver;
        this.beanFactory = context.getAutowireCapableBeanFactory();
    }

    public String getName() {
        return name;
    }

    public void addValidator(Validator validator) {
        this.validators.add(validator);
    }

    public Errors validate(Object object) {
        Errors errors = new Errors();

        for (Validator validator : validators) {
            try {
                beanFactory.autowireBean(validator);
                validator.validate(object);
            } catch (ValidationException exception) {
                ErrorMessage message = resolver.resolve(name, exception.getErrorCode(), exception.getErrorContext());
                errors.add(message);
            }
        }

        return errors;
    }

    public void validate(Object object, BindingResult result) {
        new BindingResultMapper().map(validate(object), result);
    }

    public void configure(Consumer<Validation> consumer) {
        consumer.accept(this);
    }

}
