package df.base.service.user;

import df.base.jpa.Role;
import df.base.jpa.User;
import df.base.jpa.UserRepository;
import df.base.model.user.UserDTO;
import df.base.service.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static df.base.security.Provider.LOCAL;

@Service
public class UserService {

    public static final String DEFAULT_OAUTH2_USER_ROLE = "ROLE_OAUTH_USER";
    public static final String DEFAULT_LOCAL_USER_ROLE  = "ROLE_USER";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository  repository;
    private final RoleService     roleService;

    public UserService(UserRepository repository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.addAll(role.getPrivileges().stream()
                    .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                    .collect(Collectors.toSet()));
        }

        return authorities;
    }

    public User loadUserByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);

        // todo

        return user.orElse(null);
    }

    public List<User> loadUsersByEmail(String email) {
        return repository.findAllByEmail(email);
    }

    public User createOrUpdate(UserDTO userDTO) {
        return repository.findByEmailAndProvider(userDTO.getEmail(), userDTO.getProvider())
                .map(u -> updateUser(u, userDTO)).orElseGet(() -> {
                    User newUser = createUser(userDTO);

                    final String roleName = (userDTO.getProvider() != null || userDTO.getProvider() != LOCAL)
                            ? DEFAULT_OAUTH2_USER_ROLE : DEFAULT_LOCAL_USER_ROLE;

                    Role defaultRole = roleService.createDefaultRole(roleName);

                    newUser.addRole(defaultRole);

                    repository.save(newUser);

                    return newUser;
                });
    }

    public User updateUser(User user, UserDTO userDTO) {
        return processUser(userDTO, user);
    }

    public User createUser(UserDTO userDTO) {
        return processUser(userDTO, new User());
    }

    @Transactional
    public User processUser(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setProvider(userDTO.getProvider());
        user.setEnabled(true);

        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (user.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        repository.save(user);

        return user;
    }

    @Transactional(readOnly = true)
    public Optional<User> getById(String name) {
        return repository.findById(name);
    }

    @Transactional(readOnly = true)
    public User requiredById(String id) {
        return getById(id).orElseThrow(() -> new ResourceNotFoundException("User '%s' not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return repository.findAll();
    }

}
