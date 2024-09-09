package df.base.service.form;

import df.base.dto.form.FieldOptionDTO;
import df.base.mapping.form.FieldOptionMapper;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldOption;
import df.base.persistence.repository.form.FieldOptionRepository;
import df.base.service.CommonService;
import df.base.service.RedirectAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused"})
@Service
public class FieldOptionService implements CommonService<FieldOptionDTO, FieldOption, FieldOptionRepository>, RedirectAware {

    private final FieldOptionRepository repository;
    private final FieldOptionMapper     mapper;
    private       String                redirectUrl;

    public FieldOptionService(FieldOptionRepository repository, FieldOptionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public FieldOption createOrUpdate(Field field, FieldOptionDTO dto) {
        Optional<FieldOption> option = getById(dto.id());

        if (option.isPresent()) {
            return update(option.get(), dto);
        } else {
            return create(field, dto);
        }
    }

    @Transactional
    public FieldOption create(Field field, FieldOptionDTO dto) {
        FieldOption config = mapper.reverse(dto);

        config.setField(field);

        return repository.save(config);
    }

    @Override
    @Transactional
    public FieldOption update(FieldOption config, FieldOptionDTO dto) {
        mapper.reverse(dto, config);
        return repository.save(config);
    }

    @Transactional(readOnly = true)
    public List<FieldOption> getAllByField(Field field) {
        return repository.findAllByField(field);
    }

    @Override
    public FieldOptionRepository getRepository() {
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
