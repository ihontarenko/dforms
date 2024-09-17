package df.base.common.pipeline.context;

public interface PipelineResult {

    <T> T getReturnValue();

    void setReturnValue(Object value);

    boolean hasErrors();

    Iterable<ErrorDetails> getErrors();

    ErrorDetails getError(String name);

    void addError(ErrorDetails errorDetails);

    void addError(String code, String message);

    record ErrorDetails(String code, String message) { }

}
