package df.base.security;

import df.base.jpa.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "name", target = "username")
    User toUserEntity(UserRequest dto);

    @Mapping(source = "username", target = "name")
    UserRequest toUserDTO(User entity);

}
