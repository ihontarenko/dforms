package df.base.service.user;

import df.base.Messages;
import df.base.persistence.entity.user.Privilege;
import df.base.persistence.repository.user.PrivilegeRepository;
import df.base.mapper.user.PrivilegeMapper;
import df.base.dto.user.PrivilegeDTO;
import df.base.service.RedirectAware;
import df.base.service.JpaResourceNotFoundException;
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
        return getById(privilegeDTO.getId())
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
                -> new JpaResourceNotFoundException(Messages.ERROR_PRIVILEGE_NOT_FOUND.formatted(id), this));
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
