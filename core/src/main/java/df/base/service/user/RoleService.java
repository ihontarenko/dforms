package df.base.service.user;

import df.base.jpa.Privilege;
import df.base.jpa.Role;
import df.base.jpa.RoleRepository;
import df.base.service.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class RoleService {

    private final RoleRepository   repository;
    private final PrivilegeService privilegeService;

    public RoleService(RoleRepository repository, PrivilegeService privilegeService) {
        this.repository = repository;
        this.privilegeService = privilegeService;
    }

    @Transactional
    public Role createDefaultRole(String roleName) {
        final String privilegeName = "READ";

        return repository.findByName(roleName).orElseGet(() -> {
            Supplier<Privilege> creator   = () -> privilegeService.createPrivilege(privilegeName);
            Role                role      = createRole(roleName);
            Privilege           privilege = privilegeService.getByName(privilegeName).orElseGet(creator);

            role.addPrivilege(privilege);

            repository.save(role);

            return role;
        });
    }

    @Transactional
    public Role createRole(String roleName) {
        Role role = new Role();

        role.setName(roleName);

        repository.save(role);

        return role;
    }

    @Transactional(readOnly = true)
    public Optional<Role> getByName(String name) {
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Role requiredByName(String name) {
        return getByName(name).orElseThrow(()
                -> new ResourceNotFoundException("Role '%s' not found".formatted(name)));
    }

    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Role> getAllById(Iterable<String> ids) {
        return repository.findAllById(ids);
    }

}
