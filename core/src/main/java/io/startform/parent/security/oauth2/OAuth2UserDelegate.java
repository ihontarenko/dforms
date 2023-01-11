package io.startform.parent.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class OAuth2UserDelegate implements OAuth2User {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";

    private OAuth2User user;

    public OAuth2UserDelegate(OAuth2User user) {
        System.out.println(user);
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return user.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getName() {
        return user.getAttribute(FIELD_NAME);
    }

    public String getEmail() {
        return user.getAttribute(FIELD_EMAIL);
    }

}
