package df.base.security.oauth2;

import df.base.mapper.Mapper;
import df.base.model.user.UserDTO;
import df.base.security.Provider;
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
