package df.base.common.validation;

import java.util.Map;

public class ErrorMessage {

    private ErrorCode code;
    private String    message;
    private String    pointer;

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPointer() {
        return pointer;
    }

    @Override
    public String toString() {
        return "[%s] %s: POINT: %s".formatted(code, message, pointer);
    }
}