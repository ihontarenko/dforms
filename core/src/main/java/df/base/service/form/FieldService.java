package df.base.service.form;

import df.base.persistence.entity.support.FieldStatus;
import df.base.persistence.entity.form.Field;
import df.base.persistence.repository.form.FieldRepository;
import df.base.mapping.form.FormFieldMapper;
import df.base.dto.form.FormFieldDTO;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.RedirectAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static df.base.Messages.FORM_FIELD_NOT_FOUND;
import static df.base.Messages.REQUIRED_ID_CANNOT_BE_NULL;
import static df.base.common.extensions.persistence.entity_graph.JpaEntityGraph.Dynamic.load;
import static df.base.common.extensions.persistence.entity_graph.JpaEntityGraph.Named.name;
import static df.base.persistence.support.EntityGraphConstants.FORM_FIELD_WITH_ALL_RELATED;
import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused"})
@Service
public class FieldService implements RedirectAware {

    @Autowired
    private FieldRepository repository;

    private String redirectUrl;

    @Transactional(readOnly = true)
    public List<Field> getAll() {
        return repository.findAll(name(FORM_FIELD_WITH_ALL_RELATED));
    }

    @Transactional(readOnly = true)
    public Optional<Field> getById(String id) {
        return id == null ? Optional.empty() : repository.findById(id, load("child", "parent"));
    }

    @Transactional(readOnly = true)
    public Field requireById(String id) {
        return getById(requireNonNull(id, REQUIRED_ID_CANNOT_BE_NULL))
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_FIELD_NOT_FOUND.formatted(id), this));
    }

    @Transactional
    public Field create(FormFieldDTO fieldDTO) {
        return repository.save(new FormFieldMapper().reverse(fieldDTO));
    }

    @Transactional
    public Field update(Field field, FormFieldDTO fieldDTO) {
        new FormFieldMapper().reverse(fieldDTO, field);

        return repository.save(field);
    }

    @Transactional
    public Field createOrUpdate(FormFieldDTO fieldDTO) {
        Optional<Field> optional = getById(fieldDTO.getId());
        Field           field;

        if (optional.isPresent()) {
            field = update(optional.get(), fieldDTO);
        } else {
            field = create(fieldDTO);
        }

        return field;
    }

    @Transactional
    public void changeStatus(Field field, FieldStatus status) {
        field.setStatus(status);

        repository.save(field);
    }

    @Transactional
    public void delete(Field field) {
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
