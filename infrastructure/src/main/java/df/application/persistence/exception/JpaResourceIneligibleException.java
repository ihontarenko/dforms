package df.application.persistence.exception;

import df.application.service.RedirectAware;
import df.application.exception.ApplicationException;

import static df.common.support.SlugifyTransliterator.slugify;

public class JpaResourceIneligibleException extends ApplicationException implements RedirectAware {


    public JpaResourceIneligibleException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public JpaResourceIneligibleException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public JpaResourceIneligibleException(String message) {
        super(message);
    }

    public JpaResourceIneligibleException(Throwable cause) {
        super(cause);
    }

    public JpaResourceIneligibleException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
