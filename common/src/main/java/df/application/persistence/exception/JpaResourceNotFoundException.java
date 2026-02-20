package df.application.persistence.exception;

import df.application.service.RedirectAware;
import df.application.exception.ApplicationException;

import static df.common.support.SlugifyTransliterator.slugify;

public class JpaResourceNotFoundException extends ApplicationException implements RedirectAware {

    public JpaResourceNotFoundException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public JpaResourceNotFoundException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public JpaResourceNotFoundException(String message) {
        super(message);
    }

    public JpaResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public JpaResourceNotFoundException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
