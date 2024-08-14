package df.base.service.form;

import df.base.jpa.User;
import df.base.jpa.form.*;
import df.base.model.form.FormDTO;
import df.base.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused"})
@Service
public class FormService {

    @Autowired
    private FormRepository repository;

    @Transactional(readOnly = true)
    public Optional<Form> getById(String formId) {
        return repository.findById(formId);
    }

    public Form requireById(final String formId) {
        return getById(formId).orElseThrow(()
                -> new ResourceNotFoundException("Form '%s' couldn't be found".formatted(formId)));
    }

    @Transactional(readOnly = true)
    public List<Form> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Form create(User user, FormDTO formDTO) {
        Form form = new Form();

        form.setUser(user);
        form.setName(formDTO.getName());
        form.setDescription(formDTO.getDescription());
        form.setStatus(formDTO.getStatus());

        return repository.save(form);
    }

    @Transactional
    public Form update(Form form, FormDTO formDTO) {
        form.setName(formDTO.getName());
        form.setDescription(formDTO.getDescription());
        form.setStatus(formDTO.getStatus());

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

}

