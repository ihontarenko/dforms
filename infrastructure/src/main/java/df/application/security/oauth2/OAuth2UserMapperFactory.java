package df.application.security.oauth2;

import df.application.dto.user.UserDTO;
import df.application.security.Provider;
import svit.mapping.Mapper;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

public class OAuth2UserMapperFactory {

    private final Map<Provider, Mapper<OAuth2User, UserDTO>> mappers;

    public OAuth2UserMapperFactory() {
        mappers = new HashMap<>();
    }

    public Mapper<OAuth2User, UserDTO> getMapper(Provider provider) {
        return mappers.get(provider);
    }

    public void addMapper(Provider provider, Mapper<OAuth2User, UserDTO> mapper) {
        mappers.put(provider, mapper);
    }

}
