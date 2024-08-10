package df.base.service;

import df.base.jpa.*;
import df.base.security.Provider;
import df.base.security.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    public static final String DEFAULT_OAUTH2_USER_ROLE = "ROLE_OAUTH_USER";
    public static final String DEFAULT_LOCAL_USER_ROLE  = "ROLE_USER";

    private final UserRepository      userRepository;
    private final RoleRepository      roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder     passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
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
        Optional<User> user = userRepository.findByEmail(email);

        // todo

        return user.orElse(null);
    }

    public User createOrUpdate(UserRequest userRequest) {
        return userRepository.findByEmail(userRequest.email())
                .map(u -> updateUser(u, userRequest)).orElseGet(() -> {
                    User newUser = createUser(userRequest);

                    final String roleName = (userRequest.provider() != null || userRequest.provider() != Provider.LOCAL)
                            ? DEFAULT_OAUTH2_USER_ROLE : DEFAULT_LOCAL_USER_ROLE;

                    Role newRole = createDefaultRole(roleName);

                    newUser.addRole(newRole);

                    userRepository.save(newUser);

                    return newUser;
                });
    }

    public User updateUser(User user, UserRequest userRequest) {
        return processUser(userRequest, user);
    }

    public User createUser(UserRequest userDTO) {
        return processUser(userDTO, new User());
    }

    @Transactional
    public User processUser(UserRequest userRequest, User user) {
        user.setLogin(userRequest.name());
        user.setEmail(userRequest.email());
        user.setProvider(userRequest.provider());
        user.setEnabled(true);

        if (userRequest.password() != null) {
            user.setPassword(passwordEncoder.encode(userRequest.password()));
        }

        userRepository.save(user);

        return user;
    }

    @Transactional
    public Role createDefaultRole(String roleName) {
        final String privilegeName = "READ";

        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = createRole(roleName);
            Privilege privilege = privilegeRepository.findByName(privilegeName)
                    .orElseGet(() -> createPrivilege(privilegeName));

            role.addPrivilege(privilege);

            roleRepository.save(role);

            return role;
        });
    }

    @Transactional
    public Role createRole(String roleName) {
        Role role = new Role();

        role.setName(roleName);

        roleRepository.save(role);

        return role;
    }

    @Transactional
    public Privilege createPrivilege(String privilegeName) {
        Privilege privilege = new Privilege();

        privilege.setName(privilegeName);

        privilegeRepository.save(privilege);

        return privilege;
    }

}
