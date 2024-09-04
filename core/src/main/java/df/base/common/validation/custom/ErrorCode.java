package df.base.common.validation.custom;

public interface ErrorCode {

    String name();

    enum Default implements ErrorCode {

        NULL_OBJECT, STRING_BLANK

    }

}
