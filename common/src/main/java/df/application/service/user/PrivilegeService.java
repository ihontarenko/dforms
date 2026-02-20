package df.application.service.user;

import df.application.persistence.entity.user.Privilege;
import df.application.persistence.repository.user.PrivilegeRepository;
import df.application.service.RedirectAware;
import df.application.Messages;
import df.application.old_mapping.user.PrivilegeMapper;
import df.application.dto.user.PrivilegeDTO;
import df.application.persistence.exception.JpaResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeService implements RedirectAware {

    private final PrivilegeRepository repository;
    private       String              redirectUrl;

    public PrivilegeService(PrivilegeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Privilege createOrUpdate(PrivilegeDTO privilegeDTO) {
        return getById(privilegeDTO.id())
                .map(entity -> update(entity, privilegeDTO))
                .orElseGet(() -> create(privilegeDTO));
    }

    @Transactional
    public Privilege create(PrivilegeDTO privilegeDTO) {
        Privilege privilege = new PrivilegeMapper().reverse(privilegeDTO);

        repository.save(privilege);

        return privilege;
    }

    @Transactional
    public Privilege update(Privilege privilege, PrivilegeDTO privilegeDTO) {
        new PrivilegeMapper().reverse(privilegeDTO, privilege);

        repository.save(privilege);

        return privilege;
    }

    @Transactional(readOnly = true)
    public Optional<Privilege> getById(String id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Privilege requireById(String id) {
        return getById(id).orElseThrow(()
                -> new JpaResourceNotFoundException(Messages.ERROR_PRIVILEGE_NOT_FOUND.formatted(id), this.getRedirectUrl()));
    }

    @Transactional(readOnly = true)
    public List<Privilege> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Privilege> getAllByName(Iterable<String> names) {
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
