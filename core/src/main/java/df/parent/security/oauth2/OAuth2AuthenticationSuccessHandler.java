package df.parent.security.oauth2;

import df.parent.security.service.UserSuccessHandlerService;
import df.parent.property.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserSuccessHandlerService service;
    private final SecurityProperties        properties;

    public OAuth2AuthenticationSuccessHandler(UserSuccessHandlerService service, SecurityProperties properties) {
        this.service = service;
        this.properties = properties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2UserDelegate user = (OAuth2UserDelegate) authentication.getPrincipal();

        service.processOAuthPostLogin(user.getEmail());

        response.sendRedirect(properties.getSuccessRedirect());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

}
