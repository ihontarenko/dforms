package df.base.common.validation;

public record ErrorMessage(String code, String message, String pointer) {

    @Override
    public String toString() {
        return "[%s] %s: POINT: %s".formatted(code, message, pointer);
    }

}