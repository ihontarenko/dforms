package df.base.security.oauth2.mapper;

import df.base.dto.user.UserDTO;
import df.base.security.Provider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleOAuth2UserMapper extends AbstractOAuth2UserMapper {


    @Override
    public UserDTO map(OAuth2User source) {
        UserDTO userDTO = super.map(source);

        userDTO.setProvider(Provider.GOOGLE);

        return userDTO;
    }


}
