package df.base.common.validation;

public interface ErrorContext {

    String name();

    enum Default implements ErrorContext {

        EMPTY, DEFAULT

    }

}
