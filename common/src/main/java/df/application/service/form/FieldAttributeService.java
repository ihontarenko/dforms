package df.application.service.form;

import df.application.dto.form.FieldAttributeDTO;
import df.application.old_mapping.form.FieldAttributeMapper;
import df.application.service.CommonService;
import df.application.service.RedirectAware;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldAttribute;
import df.application.persistence.repository.form.FieldAttributeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused"})
@Service
public class FieldAttributeService implements
        CommonService<FieldAttributeDTO, FieldAttribute, FieldAttributeRepository>, RedirectAware {

    private final FieldAttributeRepository repository;
    private final FieldAttributeMapper     mapper;
    private       String                   redirectUrl;

    public FieldAttributeService(FieldAttributeRepository repository, FieldAttributeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<FieldAttribute> getAllByField(Field field) {
        return repository.findAllByField(field);
    }

    public FieldAttribute createOrUpdate(Field field, FieldAttributeDTO dto) {
        Optional<FieldAttribute> attribute = getById(dto.id());

        if (attribute.isPresent()) {
            return update(attribute.get(), dto);
        } else {
            return create(field, dto);
        }
    }

    @Transactional
    public FieldAttribute create(Field field, FieldAttributeDTO dto) {
        FieldAttribute attribute = mapper.reverse(dto);

        attribute.setField(field);

        return repository.save(attribute);
    }

    @Override
    public FieldAttribute update(FieldAttribute entity, FieldAttributeDTO dto) {
        mapper.reverse(dto, entity);
        return repository.save(entity);
    }

    @Override
    public FieldAttributeRepository getRepository() {
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
