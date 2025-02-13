package df.application.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties
public class ValidationMessages {

    private Map<String, MessageCollection> validation;

    public Map<String, MessageCollection> getValidation() {
        return validation;
    }

    public void setValidation(Map<String, MessageCollection> validation) {
        this.validation = validation;
    }

    public Map<String, Message> getMessages(String packageName) {
        return validation.computeIfAbsent(packageName, key -> new MessageCollection())
                .getMessages();
    }

    public static class MessageCollection {

        private Map<String, Message> messages;

        public Map<String, Message> getMessages() {
            return messages;
        }

        public void setMessages(Map<String, Message> messages) {
            this.messages = messages;
        }

    }

    public static class Message {

        private String message;
        private String pointer;
        private String code;
        private Map<String, Message> context;

        public Map<String, Message> getContext() {
            return context;
        }

        public void setContext(Map<String, Message> context) {
            this.context = context;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPointer() {
            return pointer;
        }

        public void setPointer(String pointer) {
            this.pointer = pointer;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
