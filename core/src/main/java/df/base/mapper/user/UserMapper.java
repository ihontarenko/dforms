package df.base.mapper.user;

import df.base.jpa.Role;
import df.base.mapper.Mapper;
import df.base.jpa.User;
import df.base.model.user.UserDTO;

import java.util.stream.Collectors;

public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO map(User source) {
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(source.getEmail());
        userDTO.setName(source.getName());
        userDTO.setEnabled(source.isEnabled());
        userDTO.setProvider(source.getProvider());
        userDTO.setRoles(source.getRoles().stream().map(Role::getName)
                .collect(Collectors.toList()));

        return userDTO;
    }

    @Override
    public User reverse(UserDTO source) {
        return null;
    }

}
