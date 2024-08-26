package df.base.common.validation;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Validation {

    private final String          name;
    private       List<Validator> validators;

    public Validation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public void addValidator(Validator validator) {
        this.validators.add(validator);
    }

    public void validate(Object object) {

    }

    public void validate(Object object, BindingResult result) {

/*        List<ErrorMessage>      messages   = new ArrayList<>();

        for (Validator validator : validators) {
            try {
                validator.validate(object);
            } catch (ValidationException exception) {
                ErrorMessage message = this.messages.getMessages().get(exception.getErrorCode());

                requireNonNull(message, "NO ERROR MESSAGE FOUND FOR '%s' CODE"
                        .formatted(exception.getErrorCode()));

                Map<String, ErrorMessage> contextMessages = message.getContexts();
                ErrorContext              errorContext    = exception.getErrorContext();

                ofNullable(contextMessages.get(errorContext.name()))
                        .ifPresent(m -> merge(message, contextMessages.get(errorContext.name())));

                messages.add(message);
            }
        }*/

    }

    public void configure(Consumer<Validation> consumer) {
        consumer.accept(this);
    }

}
