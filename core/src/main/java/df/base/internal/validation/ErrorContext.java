package df.base.internal.validation;

public interface ErrorContext {

    String name();

    enum Default implements ErrorContext {

        EMPTY, DEFAULT

    }

}
