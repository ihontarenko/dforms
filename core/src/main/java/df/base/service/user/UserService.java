package df.base.service.user;

import df.base.persistence.entity.user.Role;
import df.base.persistence.entity.user.User;
import df.base.persistence.repository.user.UserRepository;
import df.base.mapping.user.UserMapper;
import df.base.dto.user.UserDTO;
import df.base.security.Provider;
import df.base.service.RedirectAware;
import df.base.persistence.exception.JpaResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static df.base.Messages.ERROR_USER_NOT_FOUND;

// todo: implement ServiceInterface
@Service
public class UserService implements RedirectAware {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository  repository;
    private final RoleService     roleService;
    private       String          redirectUrl;

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

    public Optional<User> loadUserEmailAndProvider(String email, Provider provider) {
        return repository.findByEmailAndProvider(email, provider);
    }

    @Transactional
    public User createOrUpdate(UserDTO userDTO) {
        // todo: check getId on empty string
        User user = repository.findById(userDTO.getId())
                .map(u -> update(u, userDTO))
                .orElseGet(() -> create(userDTO));

        updateUserRoles(user, userDTO);

        return user;
    }

    @Transactional
    public void updateUserRoles(User user, UserDTO userDTO) {
        Collection<Role> newRoles = roleService.getAllByName(userDTO.getRoles());
        Collection<Role> oldRoles = user.getRoles();

        oldRoles.clear();
        oldRoles.addAll(newRoles);

        repository.save(user);
    }

    @Transactional
    public User update(User user, UserDTO userDTO) {
        User updated = repository.save(populateUserEntity(user, userDTO));

        updateUserRoles(updated, userDTO);

        return updated;
    }

    @Transactional
    public User create(UserDTO userDTO) {
        User created = repository.save(populateUserEntity(new User(), userDTO));

        updateUserRoles(created, userDTO);

        return created;
    }

    public User populateUserEntity(User user, UserDTO userDTO) {
        new UserMapper(passwordEncoder).reverse(userDTO, user);

        return user;
    }

    @Transactional(readOnly = true)
    public Optional<User> getById(String id) {
        return id == null ? Optional.empty() : repository.findById(id);
    }

    @Transactional(readOnly = true)
    public User requireById(String id) {
        return getById(id).orElseThrow(()
                -> new JpaResourceNotFoundException(ERROR_USER_NOT_FOUND.formatted(id), this));
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Override
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
