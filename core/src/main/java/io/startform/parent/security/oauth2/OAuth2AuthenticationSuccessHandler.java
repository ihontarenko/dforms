package io.startform.parent.security.oauth2;

import io.startform.parent.property.SecurityProperties;
import io.startform.parent.security.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService service;
    private final SecurityProperties properties;

    public OAuth2AuthenticationSuccessHandler(UserService service, SecurityProperties properties) {
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
