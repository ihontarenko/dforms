package df.base.common.pipeline;

public class RecursiveCallThresholdException extends RuntimeException  {

    public RecursiveCallThresholdException() {
        super();
    }

    public RecursiveCallThresholdException(String message) {
        super(message);
    }

    public RecursiveCallThresholdException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecursiveCallThresholdException(Throwable cause) {
        super(cause);
    }

}
