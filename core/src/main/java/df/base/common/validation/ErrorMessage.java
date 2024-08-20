package df.base.common.validation;

public class ErrorMessage {

    private final String code;
    private final String context;
    private final String message;
    private final String detail;

    private ErrorMessage(String code, String context, String message, String detail) {
        this.code = code;
        this.context = context;
        this.message = message;
        this.detail = detail;
    }

    public static ErrorMessage create(String code, String context) {
        ErrorMessagesFactory resolver = new ErrorMessagesFactory();
//        String               message  = resolver.resolveMessage(code, context);
        String                detail   = resolver.resolveDetail(code, context);

        return new ErrorMessage(code, context, null, detail);
    }

    public String getCode() {
        return code;
    }

    public String getContext() {
        return context;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", code, context, message);
    }
}