package df.common.i18n;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Component
public class Translator {

    private final MessageSource messages;

    public Translator(MessageSource messages) {
        this.messages = messages;
    }

    public String getMessage(String label, Object... parameters) {
        return getMessage(label, getLocale(), parameters);
    }

    public String getMessage(String label, Locale locale, Object... parameters) {
        return messages.getMessage(
                label, parameters, "{*" + label.toUpperCase(Locale.ROOT) + "*}", locale);
    }

}
