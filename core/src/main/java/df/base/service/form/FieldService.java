package df.base.service.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraph;
import df.base.persistence.entity.support.FieldStatus;
import df.base.persistence.entity.form.Field;
import df.base.persistence.repository.form.FieldRepository;
import df.base.mapping.form.FieldMapper;
import df.base.dto.form.FieldDTO;
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
import static df.base.persistence.support.EntityGraphConstants.FORM_FIELD_FULL;
import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused"})
@Service
public class FieldService implements RedirectAware {

    @Autowired
    private FieldRepository repository;

    private static final JpaEntityGraph JPA_ENTITY_GRAPH = load(Field.class,
            "children", "configs", "attributes", "options");

    private String redirectUrl;

    @Transactional(readOnly = true)
    public List<Field> getAll() {
//        return repository.findAll(name(FORM_FIELD_WITH_ALL_RELATED));
        return repository.findAll(name(FORM_FIELD_FULL));
    }

    @Transactional(readOnly = true)
    public Optional<Field> getById(String id) {
        return id == null ? Optional.empty() : repository.findById(id, JPA_ENTITY_GRAPH);
    }

    @Transactional(readOnly = true)
    public Field requireById(String id) {
        return getById(requireNonNull(id, REQUIRED_ID_CANNOT_BE_NULL))
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_FIELD_NOT_FOUND.formatted(id), this));
    }

    @Transactional
    public Field create(FieldDTO fieldDTO) {
        return repository.save(new FieldMapper().reverse(fieldDTO));
    }

    @Transactional
    public Field update(Field field, FieldDTO fieldDTO) {
        new FieldMapper().reverse(fieldDTO, field);

        return repository.save(field);
    }

    @Transactional
    public Field createOrUpdate(FieldDTO fieldDTO) {
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
