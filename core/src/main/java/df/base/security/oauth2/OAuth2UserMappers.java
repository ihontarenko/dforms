package df.base.security.oauth2;

import df.base.security.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Mapper
public interface OAuth2UserMappers {

    OAuth2UserMappers INSTANCE = Mappers.getMapper(OAuth2UserMappers.class);

    @Mapping(source = "attributes.name", target = "name")
    @Mapping(source = "attributes.email", target = "email")
    @Mapping(constant = "GOOGLE", target = "provider")
    UserRequest mapGoogle(OAuth2User source);

    @Mapping(source = "attributes.name", target = "name")
    @Mapping(source = "attributes.email", target = "email")
    @Mapping(source = "attributes", target = "attributes")
    @Mapping(constant = "GITHUB", target = "provider")
    UserRequest mapGitHub(OAuth2User source);

    @Mapping(source = "attributes.name", target = "name")
    @Mapping(source = "attributes.email", target = "email")
    @Mapping(source = "attributes", target = "attributes")
    @Mapping(constant = "FACEBOOK", target = "provider")
    UserRequest mapFacebook(OAuth2User source);

    default String map(Object source) {
        return source == null ? "NULL" : source.toString();
    }

}
