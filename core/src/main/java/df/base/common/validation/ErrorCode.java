package df.base.common.validation;

public interface ErrorCode {

    String name();

    enum Default implements ErrorCode {

        NULL_OBJECT

    }

}
