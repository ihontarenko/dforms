package df.base.common.exception;

import df.base.service.RedirectAware;

public class ApplicationException extends RuntimeException implements RedirectAware {

    private String redirectUrl;

    public ApplicationException(String message, String redirectUrl, Throwable cause) {
        super(message, cause);
        this.redirectUrl = redirectUrl;
    }

    public ApplicationException(String message, String redirectUrl) {
        this(message, redirectUrl, null);
    }

    public ApplicationException(String message) {
        this(message, null, null);
    }

    public ApplicationException(Throwable cause) {
        this(null, null, cause);
    }

    public ApplicationException(Throwable cause, String redirectUrl) {
        this(null, redirectUrl, cause);
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
