package df.common.validation.custom;

public interface ErrorContext {

    String name();

    enum Default implements ErrorContext {

        EMPTY, DEFAULT

    }

}
