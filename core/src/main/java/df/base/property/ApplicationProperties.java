package df.base.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private LocaleProperties locale;

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    private String homeUrl;

    public LocaleProperties getLocale() {
        return locale;
    }

    public void setLocale(LocaleProperties locale) {
        this.locale = locale;
    }

    public static class LocaleProperties {

        private String cookieName;
        private int cookieMaxAge;

        public int getCookieMaxAge() {
            return cookieMaxAge;
        }

        public void setCookieMaxAge(int cookieMaxAge) {
            this.cookieMaxAge = cookieMaxAge;
        }

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        @Override
        public String toString() {
            return "LocaleProperties{cookieName='%s', cookieMaxAge=%d}".formatted(cookieName, cookieMaxAge);
        }
    }

    @Override
    public String toString() {
        return "ApplicationProperties{locale=%s}".formatted(locale);
    }
}

