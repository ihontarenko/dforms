package df.application.service.form;

import df.application.Messages;
import df.application.dto.form.FieldDTO;
import df.application.mapping.form.FieldMapper;
import df.application.persistence.exception.IllegalReferenceException;
import df.application.persistence.exception.JpaResourceNotFoundException;
import df.application.persistence.support.EntityGraphConstants;
import df.application.service.CommonService;
import df.application.service.RedirectAware;
import df.common.extensions.persistence.entity_graph.JpaEntityGraph;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.support.FieldStatus;
import df.application.persistence.repository.form.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static df.common.extensions.persistence.entity_graph.JpaEntityGraph.DynamicEntityGraph.fetch;
import static df.common.extensions.persistence.entity_graph.JpaEntityGraph.DynamicEntityGraph.load;
import static df.common.extensions.persistence.entity_graph.JpaEntityGraph.NamedEntityGraph.name;
import static df.application.persistence.entity.support.FieldStatus.ACTIVE;
import static df.application.persistence.entity.support.UsageType.*;
import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused"})
@Service
public class FieldService implements RedirectAware, CommonService<FieldDTO, Field, FieldRepository> {

    @Autowired
    private FieldRepository repository;

    private static final JpaEntityGraph JPA_ENTITY_GRAPH = load(Field.class,
            "children", "configs", "attributes", "options");

    private String redirectUrl;

    @Transactional(readOnly = true)
    public List<Field> getAll() {
        return repository.findAll(name(EntityGraphConstants.FORM_FIELD_FULL));
    }

    @Transactional(readOnly = true)
    public List<Field> getAllByNames(Iterable<String> names) {
        return repository.findAllByNameIn(names, fetch("configs"));
    }

    @Transactional(readOnly = true)
    public List<Field> getEmbeddableFields() {
        return repository.findAllByUsageType(EMBEDDABLE, fetch("parents"));
    }

    @Transactional(readOnly = true)
    public List<Field> getEligibleFields() {
        return repository.findAllByUsageTypeInAndStatus(List.of(STANDALONE, VIRTUAL), ACTIVE, fetch("children"));
    }

    @Transactional(readOnly = true)
    public Optional<Field> getById(String id) {
        return id == null || id.isBlank() ? Optional.empty() : repository.findById(id, JPA_ENTITY_GRAPH);
    }

    @Transactional(readOnly = true)
    public Field requireById(String id) {
        return getById(Objects.requireNonNull(id, Messages.REQUIRED_ID_CANNOT_BE_NULL))
                .orElseThrow(() -> new JpaResourceNotFoundException(Messages.FORM_FIELD_NOT_FOUND.formatted(id), this.getRedirectUrl()));
    }

    @Transactional
    public Field createOrUpdate(FieldDTO fieldDTO) {
        Optional<Field> optional = getById(fieldDTO.id());
        Field           field;

        if (optional.isPresent()) {
            field = update(optional.get(), fieldDTO);
        } else {
            field = create(fieldDTO);
        }

        return field;
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
    public void attach(String parentId, String childId) {
        Field parent = requireById(parentId);
        Field child  = requireById(childId);
        attach(parent, child);
    }

    @Transactional
    public void attach(Field parent, Field child) {
        if (parent.equals(child)) {
            throw new IllegalReferenceException(
                    "Detected a cyclic dependency: the child structured cannot serve as a parent structured");
        }

        parent.getChildren().add(child);
        repository.save(parent);
    }

    @Transactional
    public void detach(String parentId, String childId) {
        Field parent = requireById(parentId);
        Field child  = requireById(childId);
        detach(parent, child);
    }

    @Transactional
    public void detach(Field parent, Field child) {
        parent.getChildren().remove(child);
        repository.save(parent);
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
    public FieldRepository getRepository() {
        return repository;
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
