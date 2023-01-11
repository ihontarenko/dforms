package io.startform.parent.security.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);

        if (user != null) {
            System.out.println(user.<String>getAttribute("name"));
            System.out.println(user.<String>getAttribute("email"));
            System.out.println(user.<String>getAttribute("login"));
            System.out.println(user.getAttributes());
        }

        return new OAuth2UserDelegate(user);
    }

}
