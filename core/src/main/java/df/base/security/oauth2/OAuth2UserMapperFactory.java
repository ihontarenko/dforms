package df.base.security.oauth2;

import df.base.common.function.Mapper;
import df.base.security.Provider;
import df.base.security.UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

public class OAuth2UserMapperFactory {

    private final Map<Provider, Mapper<OAuth2User, UserRequest>> mappers;

    public OAuth2UserMapperFactory() {
        mappers = new HashMap<>();
    }

    public Mapper<OAuth2User, UserRequest> getMapper(Provider provider) {
        return mappers.get(provider);
    }

    public void addMapper(Provider provider, Mapper<OAuth2User, UserRequest> mapper) {
        mappers.put(provider, mapper);
    }

}
