package df.application.security;

import df.application.property.AuthorizationRedirectProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public record UserAuthenticationSuccessHandler(AuthorizationRedirectProperties properties)
        implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {
        UserInfo principal = (UserInfo) authentication.getPrincipal();

        String redirectUrl = properties.getFormLoginSuccessRedirect();

        // only oauth2 users has attributes
        if (principal.getAttributes().size() != 0) {
            redirectUrl = properties.getOAuth2SuccessRedirect();
        }

        if (!response.isCommitted()) {
            response.sendRedirect(redirectUrl);
        }
    }

}
