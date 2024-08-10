package df.base.security.oauth2;

import df.base.common.function.Mapper;
import df.base.jpa.User;
import df.base.security.Provider;
import df.base.security.UserInfo;
import df.base.security.UserRequest;
import df.base.service.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final OAuth2UserMapperFactory factory;
    private final UserService             userService;

    public OAuth2UserService(OAuth2UserMapperFactory factory, UserService userService) {
        this.factory = factory;
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        User       userEntity = getProcessedUser(userRequest, oauth2User);

        return UserInfo.create(userEntity.getEmail(), userEntity.getLogin(), userEntity.getPassword(),
                userService.getAuthorities(userEntity), oauth2User.getAttributes());
    }

    public User getProcessedUser(OAuth2UserRequest request, OAuth2User user) {
        String     registrationId = request.getClientRegistration().getRegistrationId();
        Provider   provider       = Provider.valueOf(registrationId.toUpperCase(Locale.ROOT));

        if (user == null) {
            throw new OAuth2AuthenticationException("User cannot be NULL");
        }

        Mapper<OAuth2User, UserRequest> mapper = factory.getMapper(provider);

        if (mapper == null) {
            throw new OAuth2AuthenticationException("Unsupported provider %s".formatted(registrationId));
        }

        UserRequest userRequest = mapper.map(user);

        return userService.createOrUpdate(userRequest);
    }

}
