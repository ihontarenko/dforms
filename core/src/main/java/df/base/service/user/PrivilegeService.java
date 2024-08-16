package df.base.service.user;

import df.base.Messages;
import df.base.jpa.Privilege;
import df.base.jpa.PrivilegeRepository;
import df.base.service.RedirectAware;
import df.base.service.ResourceNotFoundException;
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
    public Privilege createPrivilege(String privilegeName) {
        Privilege privilege = new Privilege();

        privilege.setName(privilegeName);

        repository.save(privilege);

        return privilege;
    }

    @Transactional
    public Optional<Privilege> getByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    public Privilege requiredByName(String name) {
        return getByName(name).orElseThrow(()
                -> new ResourceNotFoundException(Messages.ERROR_PRIVILEGE_NOT_FOUND.formatted(name), this));
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
