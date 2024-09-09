package df.base.service.form;

import df.base.Messages;
import df.base.persistence.entity.user.User;
import df.base.persistence.entity.form.Form;
import df.base.persistence.repository.form.FormRepository;
import df.base.persistence.entity.support.FormStatus;
import df.base.mapping.form.FormMapper;
import df.base.dto.form.FormDTO;
import df.base.service.RedirectAware;
import df.base.persistence.exception.JpaResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused"})
@Service
public class FormService implements RedirectAware {

    private final FormRepository repository;
    private       String         redirectUrl;

    public FormService(FormRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<Form> getById(String formId) {
        return repository.findById(formId);
    }

    public Form requireById(final String formId) {
        return getById(formId).orElseThrow(()
                -> new JpaResourceNotFoundException(Messages.ERROR_FORM_NOT_FOUND.formatted(formId), this.getRedirectUrl()));
    }

    @Transactional(readOnly = true)
    public List<Form> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Form createOrUpdate(FormDTO formDTO, User user) {
        return getById(formDTO.id())
                .map(f -> update(f, formDTO))
                .orElseGet(() -> create(user, formDTO));
    }

    @Transactional
    public Form create(User user, FormDTO formDTO) {
        Form form = new FormMapper().reverse(formDTO);

        form.setUser(user);

        return repository.save(form);
    }

    @Transactional
    public Form update(Form form, FormDTO formDTO) {
        new FormMapper().reverse(formDTO, form);

        return repository.save(form);
    }

    @Transactional
    public void delete(Form form) {
        repository.delete(form);
    }

    @Transactional
    public void changeStatus(Form form, FormStatus status) {
        form.setStatus(status);

        repository.save(form);
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

