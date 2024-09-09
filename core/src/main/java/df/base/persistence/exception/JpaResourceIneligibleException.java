package df.base.persistence.exception;

import df.base.common.exception.ApplicationException;
import df.base.service.RedirectAware;

import static df.base.common.support.SlugifyTransliterator.slugify;

public class JpaResourceIneligibleException extends ApplicationException implements RedirectAware {

    private String redirectUrl;

    public JpaResourceIneligibleException(String message) {
        super(message);
    }

    public JpaResourceIneligibleException(String message, RedirectAware redirectAware) {
        super(message);
        setRedirectUrl("%s#%s".formatted(redirectAware.getRedirectUrl(), slugify(message).toUpperCase()));
    }

    public JpaResourceIneligibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public JpaResourceIneligibleException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Override
    public void setRedirectUrl(String defaultRedirectUrl) {
        this.redirectUrl = defaultRedirectUrl;
    }
}
