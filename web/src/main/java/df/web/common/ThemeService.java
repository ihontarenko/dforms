package df.web.common;

import df.application.property.ApplicationProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ThemeService {

    private final ApplicationProperties.ThemeProperties properties;

    public ThemeService(ApplicationProperties properties) {
        this.properties = properties.getTheme();
    }

    public String getTheme(HttpServletRequest request) {
        Cookie[] cookies      = request.getCookies();
        String   defaultTheme = properties.getDefaultTheme();
        String   cookieName   = properties.getCookieName();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return defaultTheme;
    }

    public void setTheme(HttpServletResponse response, String theme) {
        Cookie cookie = new Cookie(properties.getCookieName(), theme);

        cookie.setPath(properties.getCookiePath());
        cookie.setMaxAge(properties.getCookieMaxAge());

        response.addCookie(cookie);
    }

}
