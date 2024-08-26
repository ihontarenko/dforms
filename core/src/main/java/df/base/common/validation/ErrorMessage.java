package df.base.common.validation;

public record ErrorMessage(ErrorCode code, String message, String pointer) {

    @Override
    public String toString() {
        return "[%s] %s: POINT: %s".formatted(code, message, pointer);
    }

}