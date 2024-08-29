package df.base.service;

import df.base.utils.SlugifyTransliterator;

public class ResourceNotFoundException extends RuntimeException implements RedirectAware {

    private String redirectUrl;

    public ResourceNotFoundException(String message, RedirectAware redirectAware) {
        super(message);
        setRedirectUrl(redirectAware.getRedirectUrl() + "#" + SlugifyTransliterator.slugify(message).toUpperCase());
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
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
