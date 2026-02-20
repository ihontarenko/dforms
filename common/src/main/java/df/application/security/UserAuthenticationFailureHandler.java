package df.application.security;

import df.application.property.AuthorizationRedirectProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private AuthorizationRedirectProperties properties;

    public UserAuthenticationFailureHandler(AuthorizationRedirectProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (!response.isCommitted()) {
            response.sendRedirect(properties.getFormLoginFailureRedirect());
        }
    }

}
