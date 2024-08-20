package df.base.common.validation;

public enum StandardErrorContext implements ErrorContext {

    USER_FORM;

    @Override
    public String toString() {
        return name();
    }
}
