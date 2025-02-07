package df.application.security.oauth2.mapper;

import org.jmouse.common.mapping.Mapper;
import df.application.dto.user.UserDTO;
import df.common.support.SlugifyTransliterator;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Locale;
import java.util.Map;

abstract public class AbstractOAuth2UserMapper implements Mapper<OAuth2User, UserDTO> {

    @Override
    public UserDTO map(OAuth2User source) {
        UserDTO             userDTO    = new UserDTO();
        Map<String, Object> attributes = source.getAttributes();

        userDTO.setName(SlugifyTransliterator.slugify((String) attributes.get("name")).toUpperCase(Locale.ROOT));
        userDTO.setEmail((String) attributes.get("email"));
        userDTO.setEnabled(true);

        return userDTO;
    }

    @Override
    public OAuth2User reverse(UserDTO source) {
        throw new UnsupportedOperationException();
    }

}
