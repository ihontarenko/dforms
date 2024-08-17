package df.base.service.form;

import df.base.Messages;
import df.base.jpa.form.Form;
import df.base.jpa.form.FormConfig;
import df.base.jpa.form.FormConfigRepository;
import df.base.model.form.FormConfigDTO;
import df.base.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static df.base.Messages.FORM_CONFIG_NOT_FOUND;

@Service
public class FormConfigService {

    @Autowired
    private FormConfigRepository repository;

    @Transactional
    public FormConfig create(Form form, FormConfigDTO formConfigDTO) {
        FormConfig formConfig = new FormConfig();

        formConfig.setConfigName(formConfigDTO.getConfigName());
        formConfig.setConfigValue(formConfigDTO.getConfigValue());
        formConfig.setForm(form);

        return repository.save(formConfig);
    }

    @Transactional
    public FormConfig update(FormConfig formConfig, FormConfigDTO formConfigDTO) {
        formConfig.setConfigName(formConfigDTO.getConfigName());
        formConfig.setConfigValue(formConfigDTO.getConfigValue());

        return repository.save(formConfig);
    }

    @Transactional
    public FormConfig createOrUpdate(Form form, FormConfigDTO formConfigDTO) {
        Optional<FormConfig> existingConfig = getById(formConfigDTO.getId());

        if (existingConfig.isPresent()) {
            return update(existingConfig.get(), formConfigDTO);
        } else {
            return create(form, formConfigDTO);
        }

    }

    @Transactional(readOnly = true)
    public List<FormConfig> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<FormConfig> getById(String formConfigId) {
        return repository.findById(formConfigId);
    }

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
}
