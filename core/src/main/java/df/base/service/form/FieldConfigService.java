package df.base.service.form;

import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldConfig;
import df.base.persistence.repository.form.FieldConfigRepository;
import df.base.dto.form.FieldConfigDTO;
import df.base.persistence.exception.JpaResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

import static df.base.Messages.FORM_CONFIG_NOT_FOUND;
import static df.base.Messages.REQUIRED_ID_CANNOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused"})
@Service
public class FieldConfigService implements ServiceInterface<String, FieldConfigDTO, FieldConfig, Field> {

    @Autowired
    private FieldConfigRepository repository;

    private String redirectUrl;

    @Override
    @Transactional
    public FieldConfig create(Field field, FieldConfigDTO formConfigDTO) {
        FieldConfig formConfig = new FieldConfig();

        formConfig.setConfigName(formConfigDTO.getConfigName());
        formConfig.setConfigValue(formConfigDTO.getConfigValue());
        formConfig.setField(field);

        return repository.save(formConfig);
    }

    @Override
    @Transactional
    public FieldConfig update(FieldConfig config, FieldConfigDTO configDTO) {
        config.setConfigName(configDTO.getConfigName());
        config.setConfigValue(configDTO.getConfigValue());

        return repository.save(config);
    }

    @Override
    @Transactional
    public FieldConfig createOrUpdate(Field field, FieldConfigDTO configDTO) {
        Optional<FieldConfig> config = getById(configDTO.getId());

        if (config.isPresent()) {
            return update(config.get(), configDTO);
        } else {
            return create(field, configDTO);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldConfig> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldConfig> getById(String id) {
        return StringUtils.hasText(id) ? repository.findById(id) : Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public FieldConfig requireById(String id) {
        return getById(requireNonNull(id, REQUIRED_ID_CANNOT_BE_NULL))
                .orElseThrow(() -> new JpaResourceNotFoundException(FORM_CONFIG_NOT_FOUND.formatted(id), this));
    }

    @Transactional(readOnly = true)
    public List<FieldConfig> getAllByFieldId(String fieldId) {
        return repository.findAllByFieldId(fieldId);
    }

    @Transactional(readOnly = true)
    public List<FieldConfig> getAllByField(Field field) {
        return repository.findAllByField(field);
    }

    @Transactional
    public void delete(FieldConfig config) {
        repository.delete(config);
    }

    @Transactional
    public void deleteIfExists(String id) {
        getById(id).ifPresent(this::delete);
    }

    @Transactional
    public void deleteIfExists(FieldConfig config) {
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
