package df.base.common.validation;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class ErrorMessagesFactory {

    private Map<String, Object> messages;

    public ErrorMessagesFactory() {
        loadErrorMessages();
    }

    private void loadErrorMessages() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream stream = loader.getResourceAsStream("error-messages.yml")) {
            messages = new Yaml().load(stream);
        } catch (Exception e) {
            throw new ValidatorException("FAILED TO LOAD ERROR MESSAGES CONFIGURATION", e);
        }
    }

    public String resolve(String code, String context) {

        Map<String, Object> messageData = (Map<String, Object>) messages.get(code);

        if (messageData == null) {
            throw new ValidatorException("FAILED TO LOAD MESSAGES FOR '%s' ERROR-CODE".formatted(code));
        }

        Map<String, Object> contextData = (Map<String, Object>) messageData.get("contexts");

        if (context != null && contextData != null && contextData.containsKey(context)) {
            return (String) ((Map<String, Object>) contextData.get(context)).get("message");
        }

        return (String) messageData.get("message");
    }

    public String resolveDetail(String code, String context) {
        Map<String, Object> messageData = (Map<String, Object>) messages.get(code);

        if (messageData == null) {
            return "Unknown error detail";
        }

        Map<String, Object> contextData = (Map<String, Object>) messageData.get("contexts");

        if (context != null && contextData != null && contextData.containsKey(context)) {
            return (String) ((Map<String, Object>) contextData.get(context)).get("detail");
        }

        return (String) messageData.get("detail");
    }
}
