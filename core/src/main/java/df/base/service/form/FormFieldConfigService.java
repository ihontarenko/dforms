package df.base.service.form;

import df.base.jpa.form.FormField;
import df.base.jpa.form.FormFieldConfig;
import df.base.jpa.form.FormFieldConfigRepository;
import df.base.model.form.FormFieldConfigDTO;
import df.base.service.JpaResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static df.base.Messages.FORM_CONFIG_NOT_FOUND;

@SuppressWarnings({"unused"})
@Service
public class FormFieldConfigService implements ServiceInterface<String, FormFieldConfigDTO, FormFieldConfig, FormField> {

    @Autowired
    private FormFieldConfigRepository repository;

    private String redirectUrl;

    @Override
    @Transactional
    public FormFieldConfig create(FormField field, FormFieldConfigDTO formConfigDTO) {
        FormFieldConfig formConfig = new FormFieldConfig();

        formConfig.setConfigName(formConfigDTO.getConfigName());
        formConfig.setConfigValue(formConfigDTO.getConfigValue());
        formConfig.setFormField(field);

        return repository.save(formConfig);
    }

    @Override
    @Transactional
    public FormFieldConfig update(FormFieldConfig config, FormFieldConfigDTO configDTO) {
        config.setConfigName(configDTO.getConfigName());
        config.setConfigValue(configDTO.getConfigValue());

        return repository.save(config);
    }

    @Override
    @Transactional
    public FormFieldConfig createOrUpdate(FormField field, FormFieldConfigDTO configDTO) {
        Optional<FormFieldConfig> existingConfig = StringUtils.hasText(configDTO.getId())
                ? getById(configDTO.getId()) : Optional.empty();

        if (existingConfig.isPresent()) {
            return update(existingConfig.get(), configDTO);
        } else {
            return create(field, configDTO);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<FormFieldConfig> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormFieldConfig> getById(String formConfigId) {
        return repository.findById(formConfigId);
    }

    @Override
    @Transactional(readOnly = true)
    public FormFieldConfig requireById(String id) {
        return getById(id)
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(id), this));
    }

    @Transactional(readOnly = true)
    public List<FormFieldConfig> getAllByFormId(String formId) {
        return repository.findAllByFormId(formId);
    }

    @Transactional(readOnly = true)
    public List<FormFieldConfig> getAllByField(FormField field) {
        return repository.findAllByField(field);
    }

    @Transactional
    public void delete(FormFieldConfig config) {
        repository.delete(config);
    }

    @Transactional
    public void deleteIfExists(String id) {
        getById(id).ifPresent(this::delete);
    }

    @Transactional
    public void deleteIfExists(FormFieldConfig config) {
        Optional.ofNullable(config).ifPresent(this::delete);
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
