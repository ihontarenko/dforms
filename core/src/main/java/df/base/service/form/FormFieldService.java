package df.base.service.form;

import df.base.internal.spring.jpa.entity_graph.JpaEntityGraph;
import df.base.jpa.form.*;
import df.base.mapper.form.FormFieldMapper;
import df.base.model.form.FormFieldDTO;
import df.base.service.RedirectAware;
import df.base.service.JpaResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static df.base.Messages.FORM_FIELD_NOT_FOUND;
import static df.base.Messages.REQUIRED_ID_CANNOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused"})
@Service
public class FormFieldService implements RedirectAware {

    @Autowired
    private FormFieldRepository repository;

    private String redirectUrl;

    @Transactional(readOnly = true)
    public List<FormField> getAll() {
        return repository.findAll(JpaEntityGraph.Dynamic.fetch().attribute("configs", "child.parentField").build());
    }

    @Transactional(readOnly = true)
    public Optional<FormField> getById(String id) {
        return id == null ? Optional.empty() : repository.findById(id);
    }

    @Transactional(readOnly = true)
    public FormField requireById(String id) {
        return getById(requireNonNull(id, REQUIRED_ID_CANNOT_BE_NULL))
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_FIELD_NOT_FOUND.formatted(id), this));
    }

    @Transactional
    public FormField create(FormFieldDTO fieldDTO) {
        return repository.save(new FormFieldMapper().reverse(fieldDTO));
    }

    @Transactional
    public FormField update(FormField field, FormFieldDTO fieldDTO) {
        new FormFieldMapper().reverse(fieldDTO, field);

        return repository.save(field);
    }

    @Transactional
    public FormField createOrUpdate(FormFieldDTO fieldDTO) {
        Optional<FormField> optional = getById(fieldDTO.getId());
        FormField           field;

        if (optional.isPresent()) {
            field = update(optional.get(), fieldDTO);
        } else {
            field = create(fieldDTO);
        }

        return field;
    }

    @Transactional
    public void changeStatus(FormField field, FieldStatus status) {
        field.setStatus(status);

        repository.save(field);
    }

    @Transactional
    public void delete(FormField field) {
        repository.delete(field);
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
