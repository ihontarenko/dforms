package df.base.service.form;

import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldConfig;
import df.base.persistence.repository.form.FieldConfigRepository;
import df.base.dto.form.FieldConfigDTO;
import df.base.service.CommonService;
import df.base.service.RedirectAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused"})
@Service
public class FieldConfigService implements
        CommonService<FieldConfigDTO, FieldConfig, FieldConfigRepository>, RedirectAware {

    @Autowired
    private FieldConfigRepository repository;

    private String redirectUrl;

    @Transactional
    public FieldConfig create(Field field, FieldConfigDTO formConfigDTO) {
        FieldConfig formConfig = new FieldConfig();

        formConfig.setConfigName(formConfigDTO.getKey());
        formConfig.setConfigValue(formConfigDTO.getValue());
        formConfig.setField(field);

        return repository.save(formConfig);
    }

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
    @Transactional
    public FieldConfig update(FieldConfig config, FieldConfigDTO configDTO) {
        config.setConfigName(configDTO.getKey());
        config.setConfigValue(configDTO.getValue());

        return repository.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldConfig> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<FieldConfig> getAllByField(Field field) {
        return repository.findAllByField(field);
    }

    @Override
    public FieldConfigRepository getRepository() {
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
