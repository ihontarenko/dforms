package df.base.mapping;

import df.base.common.exception.ApplicationException;

public class MapperException extends ApplicationException {

    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

}
