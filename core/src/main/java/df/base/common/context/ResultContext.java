package df.base.common.context;

public interface ResultContext {

    <T> T getReturnValue();

    void setReturnValue(Object value);

    boolean hasErrors();

    Iterable<ErrorDetails> getErrors();

    ErrorDetails getError(String name);

    void addError(ErrorDetails errorDetails);

    void addError(String code, String message);

    void cleanup();

    record ErrorDetails(String code, String message) { }

}
