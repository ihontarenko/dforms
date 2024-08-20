package df.base.common.validation;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    private Map<String, ErrorMessage> contexts = new HashMap<>();
    private String                    code;
    private String                    message;
    private String                    detail;
    private String                    pointer;

    public Map<String, ErrorMessage> getContexts() {
        return contexts;
    }

    public void setContexts(Map<String, ErrorMessage> contexts) {
        this.contexts = contexts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public static class Collection {

        private String name;
        private Map<String, ErrorMessage> messages;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, ErrorMessage> getMessages() {
            return messages;
        }

        public void setMessages(Map<String, ErrorMessage> messages) {
            this.messages = messages;
        }

    }

    @Override
    public String toString() {
        return "[%s] %s: %s; POINT: %s".formatted(code, message, detail, pointer);
    }
}