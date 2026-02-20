package df.application.old_mapping;

import df.application.exception.ApplicationException;

public class MapperException extends ApplicationException {

    public MapperException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public MapperException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

    public MapperException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
