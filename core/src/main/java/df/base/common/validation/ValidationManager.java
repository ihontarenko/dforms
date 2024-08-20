package df.base.common.validation;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElseGet;

public final class ValidationManager {

    private final ValidatorFactory        factory;
    private final ErrorMessage.Collection messages;
    private final BindingResultMapper     mapper;

    public ValidationManager(ValidatorFactory factory, ErrorMessage.Collection messages) {
        this.factory = factory;
        this.messages = messages;
        this.mapper = new BindingResultMapper();
    }

    public void validate(String name, Object object, BindingResult bindingResult) {
        mapper.map(validate(name, object), bindingResult);
    }

    public List<ErrorMessage> validate(String name, Object object) {
        List<ErrorMessage>      messages   = new ArrayList<>();
        List<Validator<Object>> validators = factory.getValidatorHolder(name).getValidators();

        for (Validator<Object> validator : validators) {
            try {
                validator.validate(object);
            } catch (ValidationException exception) {
                ErrorMessage message = this.messages.getMessages().get(exception.getErrorCode());

                requireNonNull(message, "NO ERROR MESSAGE FOUND FOR '%s' CODE"
                        .formatted(exception.getErrorCode()));

                Map<String, ErrorMessage> contextMessages = message.getContexts();

                if (exception.getErrorContext() != null && contextMessages.containsKey(exception.getErrorContext())) {
                    merge(message, contextMessages.get(exception.getErrorContext()));
                }

                messages.add(message);
            }
        }

        return messages;
    }

    public void merge(ErrorMessage targetMessage, ErrorMessage contextMessage) {
        requireNonNull(contextMessage, "CONTEXT ERROR-MESSAGE MUST BE NON NULL");

        targetMessage.setCode(requireNonNullElseGet(contextMessage.getCode(), targetMessage::getCode));
        targetMessage.setCode(requireNonNullElseGet(contextMessage.getMessage(), targetMessage::getMessage));
        targetMessage.setCode(requireNonNullElseGet(contextMessage.getDetail(), targetMessage::getDetail));
        targetMessage.setCode(requireNonNullElseGet(contextMessage.getPointer(), targetMessage::getPointer));
    }

}
