package df.base.persistence.exception;

import df.base.common.exception.ApplicationException;
import df.base.service.RedirectAware;

import static df.base.common.support.SlugifyTransliterator.slugify;

public class IllegalReferenceException extends ApplicationException implements RedirectAware {

    public IllegalReferenceException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public IllegalReferenceException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public IllegalReferenceException(String message) {
        super(message);
    }

    public IllegalReferenceException(Throwable cause) {
        super(cause);
    }

    public IllegalReferenceException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
