package df.application.service.user;

import df.application.persistence.entity.user.Privilege;
import df.application.service.RedirectAware;
import df.application.Messages;
import df.application.persistence.entity.user.Role;
import df.application.persistence.repository.user.RoleRepository;
import df.application.dto.user.RoleDTO;
import df.application.persistence.exception.JpaResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused"})
@Service
public class RoleService implements RedirectAware {

    private final RoleRepository   repository;
    private final PrivilegeService privilegeService;
    private       String           redirectUrl;

    public RoleService(RoleRepository repository, PrivilegeService privilegeService) {
        this.repository = repository;
        this.privilegeService = privilegeService;
    }

    @Transactional
    public Role createOrUpdate(RoleDTO roleDTO) {
        return getById(roleDTO.id())
                .map(entity -> update(entity, roleDTO))
                .orElseGet(() -> create(roleDTO));
    }

    @Transactional
    public Role create(RoleDTO roleDTO) {
        Role   role     = new Role();
        String roleName = roleDTO.getName();

        if (!roleName.contains(RoleDTO.ROLE_PREFIX)) {
            roleName = RoleDTO.ROLE_PREFIX + roleName;
        }

        role.setName(roleName);

        repository.save(role);

        updateRolePrivileges(role, roleDTO);

        return role;
    }

    @Transactional
    public Role update(Role role, RoleDTO roleDTO) {
        String roleName = roleDTO.getName();

        if (!roleName.startsWith(RoleDTO.ROLE_PREFIX)) {
            roleName = RoleDTO.ROLE_PREFIX + roleName;
        }

        role.setName(roleName);

        repository.save(role);

        return updateRolePrivileges(role, roleDTO);
    }

    @Transactional
    public Role updateRolePrivileges(Role role, RoleDTO roleDTO) {
        Collection<Privilege> newPrivileges = privilegeService.getAllByName(roleDTO.getPrivileges());
        Collection<Privilege> oldPrivileges = role.getPrivileges();

        oldPrivileges.clear();
        oldPrivileges.addAll(newPrivileges);

        return repository.save(role);
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
                -> new JpaResourceNotFoundException(Messages.ERROR_ROLE_NOT_FOUND.formatted(id), this.getRedirectUrl()));
    }

    @Transactional(readOnly = true)
    public Role requiredByName(String name) {
        return getById(name).orElseThrow(()
                -> new JpaResourceNotFoundException(Messages.ERROR_ROLE_NOT_FOUND.formatted(name), this.getRedirectUrl()));
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
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
