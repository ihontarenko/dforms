package df.base.service.user;

import df.base.Messages;
import df.base.jpa.Privilege;
import df.base.jpa.Role;
import df.base.jpa.RoleRepository;
import df.base.model.user.RoleDTO;
import df.base.service.RedirectAware;
import df.base.service.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class RoleService implements RedirectAware {

    private final RoleRepository   repository;
    private final PrivilegeService privilegeService;
    private       String          redirectUrl;

    public RoleService(RoleRepository repository, PrivilegeService privilegeService) {
        this.repository = repository;
        this.privilegeService = privilegeService;
    }

    @Transactional
    public Role createRole(String roleName) {
        Role role = new Role();

        if (!roleName.contains(RoleDTO.ROLE_PREFIX)) {
            roleName = RoleDTO.ROLE_PREFIX + roleName;
        }

        role.setName(roleName);

        repository.save(role);

        return role;
    }

    @Transactional(readOnly = true)
    public Optional<Role> getByName(String name) {
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<Role> getById(String id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Role requiredById(String id) {
        return getById(id).orElseThrow(()
                -> new ResourceNotFoundException(Messages.ERROR_ROLE_NOT_FOUND.formatted(id), this));
    }

    @Transactional(readOnly = true)
    public Role requiredByName(String name) {
        return getById(name).orElseThrow(()
                -> new ResourceNotFoundException(Messages.ERROR_ROLE_NOT_FOUND.formatted(name), this));
    }

    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Role> getAllById(Iterable<String> ids) {
        return repository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public List<Role> getAllByName(Iterable<String> names) {
        return repository.findByNameIn(names);
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Override
    public void setRedirectUrl(String defaultRedirectUrl) {
        this.redirectUrl = defaultRedirectUrl;
    }
}
