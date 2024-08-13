package df.base.service.forms;

import df.base.jpa.User;
import df.base.jpa.form.*;
import df.base.model.form.FormDTO;
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
    public Optional<Form> getFormById(String formId) {
        return repository.findById(formId);
    }

    @Transactional(readOnly = true)
    public List<Form> getAllForms() {
        return repository.findAll();
    }

    @Transactional
    public Form createForm(User user, FormDTO formDTO) {
        Form form = new Form();

        form.setUser(user);
        form.setName(formDTO.getName());
        form.setDescription(formDTO.getDescription());
        form.setStatus(formDTO.getStatus());

        return repository.save(form);
    }

    @Transactional
    public Form updateForm(Form form, FormDTO formDTO) {
        form.setName(formDTO.getName());
        form.setDescription(formDTO.getDescription());
        form.setStatus(formDTO.getStatus());

        return repository.save(form);
    }

    @Transactional
    public void deleteForm(Form form) {
        repository.delete(form);
    }

    @Transactional
    public Form changeStatus(Form form, FormStatus status) {
        form.setStatus(status);

        return repository.save(form);
    }

}

