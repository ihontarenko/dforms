package df.base.mapper.user;

import df.base.Messages;
import df.base.jpa.Role;
import df.base.jpa.User;
import df.base.mapper.Mapper;
import df.base.model.user.UserDTO;
import df.base.utils.SlugifyTransliterator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Locale;

import static df.base.Messages.ERROR_MAPPER_PASSWORD_ENCODER_IS_REQUIRED;
import static df.base.utils.SlugifyTransliterator.slugify;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

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