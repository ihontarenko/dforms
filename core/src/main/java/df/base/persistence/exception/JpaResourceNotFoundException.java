package df.base.persistence.exception;

import df.base.common.exception.ApplicationException;
import df.base.service.RedirectAware;

import static df.base.common.support.SlugifyTransliterator.slugify;

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
