package df.application.persistence.exception;

import df.application.service.RedirectAware;
import df.application.exception.ApplicationException;

import static df.common.support.SlugifyTransliterator.slugify;

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
