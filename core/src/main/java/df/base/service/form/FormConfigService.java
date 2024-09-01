package df.base.service.form;

import df.base.jpa.form.Form;
import df.base.jpa.form.FormConfig;
import df.base.jpa.form.FormConfigRepository;
import df.base.model.form.FormConfigDTO;
import df.base.service.JpaResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static df.base.Messages.FORM_CONFIG_NOT_FOUND;
import static df.base.Messages.REQUIRED_ID_CANNOT_BE_NULL;
import static java.util.Objects.requireNonNull;

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
        Optional<FormConfig> config = getById(configDTO.getId());

        if (config.isPresent()) {
            return update(config.get(), configDTO);
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
    public Optional<FormConfig> getById(String id) {
        return StringUtils.hasText(id) ? repository.findById(id) : Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public FormConfig requireById(String id) {
        return getById(requireNonNull(id, REQUIRED_ID_CANNOT_BE_NULL))
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(id), this));
    }

    @Transactional(readOnly = true)
    public Optional<FormConfig> getByName(String formConfigName) {
        return repository.findByConfigName(formConfigName);
    }

    @Transactional(readOnly = true)
    public FormConfig requireByName(String name) {
        return getByName(name)
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(name), this));
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
