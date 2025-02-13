package df.application.service.form;

import df.application.dto.form.FormConfigDTO;
import df.application.service.CommonService;
import df.application.service.RedirectAware;
import df.application.persistence.entity.form.Form;
import df.application.persistence.entity.form.FormConfig;
import df.application.persistence.repository.form.FormConfigRepository;
import df.application.persistence.exception.JpaResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static df.application.Messages.FORM_CONFIG_NOT_FOUND;
import static df.application.Messages.REQUIRED_ID_CANNOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused"})
@Service
public class FormConfigService implements
        CommonService<FormConfigDTO, FormConfig, FormConfigRepository>, RedirectAware {

    @Autowired
    private FormConfigRepository repository;

    private static final String[] DEFAULT_CONFIG_KEYS = new String[] {
            "CONFIG_FORM_ACTION",
            "CONFIG_FORM_METHOD",
            "CONFIG_SUBMIT_TEXT",
    };

    private String redirectUrl;

    @Transactional
    public FormConfig create(Form form, FormConfigDTO formConfigDTO) {
        FormConfig config = new FormConfig();

        config.setConfigName(formConfigDTO.getConfigName());
        config.setConfigValue(formConfigDTO.getConfigValue());
        config.setForm(form);

        return repository.save(config);
    }

    @Override
    @Transactional
    public FormConfig update(FormConfig config, FormConfigDTO configDTO) {
        config.setConfigName(configDTO.getConfigName());
        config.setConfigValue(configDTO.getConfigValue());

        return repository.save(config);
    }

    @Transactional
    public FormConfig createOrUpdate(Form form, FormConfigDTO configDTO) {
        Optional<FormConfig> config = getById(configDTO.id());

        if (config.isPresent()) {
            return update(config.get(), configDTO);
        } else {
            return create(form, configDTO);
        }

    }

    public void createDefaultConfigs(Form form) {
        for (String defaultConfigKey : DEFAULT_CONFIG_KEYS) {
            create(form, new FormConfigDTO(){{
                setConfigName(defaultConfigKey);
                setConfigValue("");
            }});
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
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(id), this.getRedirectUrl()));
    }

    @Transactional(readOnly = true)
    public Optional<FormConfig> getByName(String formConfigName) {
        return repository.findByConfigName(formConfigName);
    }

    @Transactional(readOnly = true)
    public FormConfig requireByName(String name) {
        return getByName(name)
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(name), this.getRedirectUrl()));
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
    public FormConfigRepository getRepository() {
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
