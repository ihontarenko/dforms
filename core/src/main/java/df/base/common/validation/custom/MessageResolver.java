package df.base.common.validation.custom;

import df.base.property.ValidationMessages;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class MessageResolver {

    private static final String             DEFAULT_PACKAGE = "DEFAULT";
    private final        ValidationMessages messages;

    public MessageResolver(ValidationMessages messages) {
        this.messages = messages;
    }

    public ErrorMessage resolve(String packageName, ErrorCode code, ErrorContext context) {
        Map<String, ValidationMessages.Message> messages = this.messages.getMessages(packageName);

        ValidationMessages.Message message = Objects.requireNonNull(messages.get(code.name()),
                "UNABLE TO FIND MESSAGE CODE '%s'".formatted(code.name()));

        message.setCode(code.name());

        ErrorMessage error = new ErrorMessageMapper().map(message);

        if (context != null && message.getContext() != null && !message.getContext().isEmpty()) {
            if (message.getContext().containsKey(context.name())) {
                ValidationMessages.Message contextMessage = message.getContext().get(context.name());
                contextMessage.setCode(context.name());
                error = new ErrorMessagePatcher().patch(error, contextMessage);
            }
        }

        return error;
    }

    public ErrorMessage resolve(ErrorCode code, ErrorContext context) {
        return resolve(DEFAULT_PACKAGE, code, context);
    }

    public ErrorMessage resolve(ErrorCode code) {
        return resolve(code, null);
    }

}
