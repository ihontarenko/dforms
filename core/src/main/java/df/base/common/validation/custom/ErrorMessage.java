package df.base.common.validation.custom;

public record ErrorMessage(String pointer, String message, String code) {

    @Override
    public String toString() {
        return "[%s] %s: POINT: %s".formatted(code, message, pointer);
    }

}