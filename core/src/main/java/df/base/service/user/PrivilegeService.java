package df.base.service.user;

import df.base.jpa.Privilege;
import df.base.jpa.PrivilegeRepository;
import df.base.jpa.Role;
import df.base.jpa.RoleRepository;
import df.base.service.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Transactional
    public Privilege createPrivilege(String privilegeName) {
        Privilege privilege = new Privilege();

        privilege.setName(privilegeName);

        privilegeRepository.save(privilege);

        return privilege;
    }

    @Transactional
    public Optional<Privilege> getByName(String name) {
        return privilegeRepository.findByName(name);
    }

    @Transactional
    public Privilege requiredByName(String name) {
        return getByName(name).orElseThrow(()
                -> new ResourceNotFoundException("Privilege '%s' not found".formatted(name)));
    }

}
