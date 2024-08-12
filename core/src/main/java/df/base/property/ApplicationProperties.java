package df.base.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private LocaleProperties locale;
    private String           homeUrl;
    private ThemeProperties  theme;

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public LocaleProperties getLocale() {
        return locale;
    }

    public void setLocale(LocaleProperties locale) {
        this.locale = locale;
    }

    public ThemeProperties getTheme() {
        return theme;
    }

    public void setTheme(ThemeProperties theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{locale=%s}".formatted(locale);
    }

    public static class ThemeProperties {

        private String cookieName;
        private String defaultTheme;
        private String cookiePath;
        private int    cookieMaxAge;

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        public String getDefaultTheme() {
            return defaultTheme;
        }

        public void setDefaultTheme(String defaultTheme) {
            this.defaultTheme = defaultTheme;
        }

        public int getCookieMaxAge() {
            return cookieMaxAge;
        }

        public void setCookieMaxAge(int cookieMaxAge) {
            this.cookieMaxAge = cookieMaxAge;
        }

        public String getCookiePath() {
            return cookiePath;
        }

        public void setCookiePath(String cookiePath) {
            this.cookiePath = cookiePath;
        }
    }

    public static class LocaleProperties {

        private String cookieName;
        private int    cookieMaxAge;

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
}

