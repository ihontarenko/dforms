package df.base.service;

public interface RedirectAware {

    default boolean hasRedirectUrl() {
        return getRedirectUrl() != null && !getRedirectUrl().isBlank();
    }

    String getRedirectUrl();

    void setRedirectUrl(String defaultRedirectUrl);

}
