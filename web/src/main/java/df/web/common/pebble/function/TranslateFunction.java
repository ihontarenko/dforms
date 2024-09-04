package df.web.common.pebble.function;

import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import df.base.common.i18n.Translator;
import org.springframework.context.ApplicationContext;

import java.util.*;

public class TranslateFunction implements Function {

    public static final String     FUNCTION_NAME       = "translate";
    public static final String     FUNCTION_NAME_SHORT = "t";

    private final ApplicationContext context;

    public TranslateFunction(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object execute(Map<String, Object> arguments, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        Set<Map.Entry<String, Object>> entries = arguments.entrySet();

        if (entries.isEmpty()) {
            return "[TRANSLATION_ERROR:%s:%d]"
                    .formatted(self.getName(), lineNumber);
        }

        Iterator<Map.Entry<String, Object>> iterator   = entries.iterator();
        String                              key        = (String) iterator.next().getValue();
        Object[]                            parameters = new Object[entries.size() - 1];
        int                                 keyCounter = 0;

        while (iterator.hasNext()) {
            parameters[keyCounter++] = iterator.next().getValue();
        }

        return getMessage(key, parameters);
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    private String getMessage(String key, Object... parameters) {
        return context.getBean(Translator.class).getMessage(key, parameters);
    }

}
