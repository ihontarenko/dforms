package df.application.mapping.user;

import df.application.persistence.entity.user.Role;
import df.application.persistence.entity.user.User;
import org.jmouse.core.mapping.Mapper;
import df.application.dto.user.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

import static df.application.Messages.ERROR_MAPPER_PASSWORD_ENCODER_IS_REQUIRED;
import static df.common.support.SlugifyTransliterator.slugify;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Service
public class UserMapper implements Mapper<User, UserDTO> {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserMapper() {
        this(null);
    }

    @Override
    public UserDTO map(User source) {
        UserDTO userDTO = new UserDTO();

        userDTO.setRoles(source.getRoles()
                .stream().map(Role::getName).collect(toList()));
        userDTO.setEmail(source.getEmail());
        userDTO.setName(source.getName());
        userDTO.setEnabled(source.isEnabled());
        userDTO.setProvider(source.getProvider());
        userDTO.setId(source.getId());

        return userDTO;
    }

    @Override
    public User reverse(UserDTO source) {
        User user = new User();

        reverse(source, user);

        return user;
    }

    @Override
    public void reverse(UserDTO source, User destination) {
        if (destination.getId() == null) {
            destination.setCreatedAt(LocalDateTime.now());
        }

        destination.setProvider(source.getProvider());
        destination.setName(slugify(source.getName()).toUpperCase(Locale.ROOT));
        destination.setEmail(source.getEmail());
        destination.setEnabled(source.isEnabled());

        if (source.getPassword() != null && !source.getPassword().isBlank()) {
            destination.setPassword(requireNonNull(passwordEncoder, ERROR_MAPPER_PASSWORD_ENCODER_IS_REQUIRED)
                    .encode(source.getPassword()));
        }
    }
    
}
