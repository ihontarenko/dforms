package df.base.security.oauth2;

import df.base.jpa.User;
import df.base.security.UserInfo;
import df.base.service.UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class OpenIDUserService extends OidcUserService {

    private final OAuth2UserService oauth2UserService;
    private final UserService       userService;

    public OpenIDUserService(OAuth2UserService oauth2UserService, UserService userService) {
        this.oauth2UserService = oauth2UserService;
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser   = super.loadUser(userRequest);
        User     userEntity = oauth2UserService.getProcessedUser(userRequest, oidcUser);

        return UserInfo.create(userEntity.getEmail(), userEntity.getLogin(), userEntity.getPassword(),
                userService.getAuthorities(userEntity), oidcUser.getAttributes(), oidcUser.getClaims());
    }
}
