package df.base.common.validation;

public record ValidationError(String code, int status, String title, String detail, String source) {

    public static ValidationError create(String code, int status, String title, String detail, String source) {
        return new ValidationError(code, status, title, detail, source);
    }

    public static ValidationError create(String title, String detail, String source) {
        return new ValidationError(null, 0, title, detail, source);
    }

    public static ValidationError create(String title, String detail) {
        return new ValidationError(null, 0, title, detail, null);
    }

}

