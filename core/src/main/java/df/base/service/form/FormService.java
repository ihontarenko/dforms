package df.base.service.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraph;
import df.base.common.pipeline.PipelineManager;
import df.base.common.pipeline.context.DefaultPipelineContext;
import df.base.dto.form.FormDTO;
import df.base.mapping.form.FormMapper;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.support.FormStatus;
import df.base.persistence.entity.user.User;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.persistence.repository.form.FormRepository;
import df.base.service.RedirectAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static df.base.Messages.ERROR_FORM_NOT_FOUND;
import static df.base.Messages.ERROR_SEQUENCE_ORDER_NOT_FOUND;
import static df.base.common.extensions.persistence.entity_graph.JpaEntityGraph.Dynamic.fetch;
import static java.lang.Math.max;
import static java.lang.Math.min;

@SuppressWarnings({"unused"})
@Service
public class FormService implements RedirectAware {

    private final FormRepository    repository;
    private final FieldService      fieldService;
    private final FormConfigService configService;
    private final PipelineManager   pipelineManager;
    private       String            redirectUrl;

    public FormService(
            FormRepository repository, FieldService fieldService, FormConfigService configService,
            PipelineManager pipelineManager) {
        this.repository = repository;
        this.configService = configService;
        this.fieldService = fieldService;
        this.pipelineManager = pipelineManager;
    }

    @Transactional(readOnly = true)
    public Optional<Form> getById(String formId) {
        return repository.findById(formId);
    }

    @Transactional(readOnly = true)
    public Form loadFormWithFields(String formId) {
        JpaEntityGraph entityGraph  = fetch("fields", "fields.configs", "fields.attributes", "fields.options",
                                            "fields.children", "fields.children.configs", "fields.children.attributes",
                                            "fields.children.options");
        Optional<Form> optionalForm = repository.findById(formId, entityGraph);

        return optionalForm.orElseThrow(()
            -> new JpaResourceNotFoundException(ERROR_FORM_NOT_FOUND.formatted(formId)));
    }

    @Transactional(readOnly = true)
    public Form requireById(final String formId) {
        return getById(formId).orElseThrow(()
                -> new JpaResourceNotFoundException(ERROR_FORM_NOT_FOUND.formatted(formId), this.getRedirectUrl()));
    }

    @Transactional(readOnly = true)
    public List<Form> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Form createOrUpdate(FormDTO formDTO, User user) {
        Optional<Form> optional = getById(formDTO.id());
        Form           updated;

        DefaultPipelineContext context         = (DefaultPipelineContext) pipelineManager.getContext();

        context.setArgument(this);
        context.setArgument(Optional.class, optional);
        context.setArgument(formDTO);

        try {
            pipelineManager.runPipeline("process-form-entity");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (optional.isPresent()) {
//            updated = this.update(optional.get(), formDTO);
//        } else {
//            updated = this.create(user, formDTO);
//            this.configService.createDefaultConfigs(updated);
//        }

        return context.getReturnValue();
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
    public void attach(String formId, String fieldId) {
        attach(requireById(formId), fieldService.requireById(fieldId));
    }

    @Transactional
    public void attach(Form form, Field field) {
        if (form.absentField(field)) {
            form.addField(field);
            repository.save(form);
        }
    }

    @Transactional
    public void detach(String formId, String fieldId) {
        detach(requireById(formId), fieldService.requireById(fieldId));
    }

    @Transactional
    public void detach(Form form, Field field) {
        if (form.existsField(field)) {
            form.removeField(field);
            repository.save(form);
        }
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

    @Transactional
    public void reorder(String formId, String fieldId, int newIndex) {
        Integer     oldIndex = repository.findCurrentOrder(formId, fieldId);
        Form        form     = requireById(formId);
        List<Field> fields   = form.getFields();

        if (oldIndex == null) {
            throw new JpaResourceNotFoundException(
                    ERROR_SEQUENCE_ORDER_NOT_FOUND.formatted("form", "field", formId, fieldId));
        }

        Field field = fields.remove(oldIndex.intValue());

        newIndex = min(max(newIndex, 0), fields.size());

        fields.add(newIndex, field);
        fields.removeIf(Objects::isNull);

        form.setFields(fields);

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

