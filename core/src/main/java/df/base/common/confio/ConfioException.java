package df.base.common.confio;

public class ConfioException extends Error {

    public ConfioException(String message) {
        super(message);
    }

    public ConfioException(String message, Throwable cause) {
        super(message, cause);
    }

}
