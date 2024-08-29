package df.base.service.form;

import df.base.jpa.form.Form;
import df.base.jpa.form.FormConfig;
import df.base.jpa.form.FormConfigRepository;
import df.base.model.form.FormConfigDTO;
import df.base.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static df.base.Messages.FORM_CONFIG_NOT_FOUND;

@SuppressWarnings({"unused"})
@Service
public class FormConfigService implements ServiceInterface<String, FormConfigDTO, FormConfig, Form> {

    @Autowired
    private FormConfigRepository repository;

    private String redirectUrl;

    @Override
    @Transactional
    public FormConfig create(Form form, FormConfigDTO formConfigDTO) {
        FormConfig formConfig = new FormConfig();

        formConfig.setConfigName(formConfigDTO.getConfigName());
        formConfig.setConfigValue(formConfigDTO.getConfigValue());
        formConfig.setForm(form);

        return repository.save(formConfig);
    }

    @Override
    @Transactional
    public FormConfig update(FormConfig config, FormConfigDTO configDTO) {
        config.setConfigName(configDTO.getConfigName());
        config.setConfigValue(configDTO.getConfigValue());

        return repository.save(config);
    }

    @Override
    @Transactional
    public FormConfig createOrUpdate(Form form, FormConfigDTO configDTO) {
        Optional<FormConfig> existingConfig = StringUtils.hasText(configDTO.getId())
                ? getById(configDTO.getId()) : Optional.empty();

        if (existingConfig.isPresent()) {
            return update(existingConfig.get(), configDTO);
        } else {
            return create(form, configDTO);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<FormConfig> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormConfig> getById(String formConfigId) {
        return repository.findById(formConfigId);
    }

    @Override
    @Transactional(readOnly = true)
    public FormConfig requireById(String id) {
        return getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(id), this));
    }

    @Transactional(readOnly = true)
    public Optional<FormConfig> getByName(String formConfigName) {
        return repository.findByConfigName(formConfigName);
    }

    @Transactional(readOnly = true)
    public FormConfig requireByName(String name) {
        return getByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(name), this));
    }

    @Transactional(readOnly = true)
    public List<FormConfig> getAllByFormId(String formId) {
        return repository.findAllByFormId(formId);
    }

    @Transactional(readOnly = true)
    public List<FormConfig> getAllByForm(Form form) {
        return repository.findAllByForm(form);
    }

    @Transactional
    public void delete(FormConfig config) {
        repository.delete(config);
    }

    @Transactional
    public void deleteIfExists(String id) {
        getById(id).ifPresent(this::delete);
    }

    @Transactional
    public void deleteIfExists(FormConfig config) {
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
