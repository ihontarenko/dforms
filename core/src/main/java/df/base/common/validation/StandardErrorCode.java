package df.base.common.validation;

public enum StandardErrorCode implements ErrorCode {

    NOT_NULL,
    NOT_EMPTY,
    UNSUPPORTED_CHARACTERS;

    @Override
    public String toString() {
        return name();
    }

}
