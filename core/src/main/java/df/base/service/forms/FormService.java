package df.base.service.forms;

import df.base.jpa.Role;
import df.base.jpa.User;
import df.base.jpa.forms.*;
import df.base.model.FormDTO;
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

}

