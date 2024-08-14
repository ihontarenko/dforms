package df.base.security.oauth2.mapper;

import df.base.mapper.Mapper;
import df.base.model.user.UserDTO;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

abstract public class AbstractOAuth2UserMapper implements Mapper<OAuth2User, UserDTO> {

    @Override
    public UserDTO map(OAuth2User source) {
        UserDTO             userDTO    = new UserDTO();
        Map<String, Object> attributes = source.getAttributes();

        userDTO.setName((String) attributes.get("name"));
        userDTO.setEmail((String) attributes.get("email"));
        userDTO.setEnabled(true);

        return userDTO;
    }

    @Override
    public OAuth2User reverse(UserDTO source) {
        throw new UnsupportedOperationException();
    }

}
