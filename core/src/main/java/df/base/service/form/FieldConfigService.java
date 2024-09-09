package df.base.service.form;

import df.base.mapping.form.FieldConfigMapper;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldConfig;
import df.base.persistence.repository.form.FieldConfigRepository;
import df.base.dto.form.FieldConfigDTO;
import df.base.service.CommonService;
import df.base.service.RedirectAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SuppressWarnings({"unused"})
@Service
public class FieldConfigService implements
        CommonService<FieldConfigDTO, FieldConfig, FieldConfigRepository>, RedirectAware {

    private final FieldConfigRepository repository;
    private final FieldConfigMapper mapper;

    private String redirectUrl;

    public FieldConfigService(FieldConfigRepository repository, FieldConfigMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public FieldConfig createOrUpdate(Field field, FieldConfigDTO configDTO) {
        Optional<FieldConfig> config = getById(configDTO.id());

        if (config.isPresent()) {
            return update(config.get(), configDTO);
        } else {
            return create(field, configDTO);
        }
    }

    @Transactional
    public FieldConfig create(Field field, FieldConfigDTO dto) {
        FieldConfig config = mapper.reverse(dto);

        config.setField(field);

        return repository.save(config);
    }

    @Override
    @Transactional
    public FieldConfig update(FieldConfig config, FieldConfigDTO dto) {
        mapper.reverse(dto, config);
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
