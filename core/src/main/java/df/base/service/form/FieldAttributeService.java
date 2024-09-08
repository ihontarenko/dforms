package df.base.service.form;

import df.base.dto.form.FieldAttributeDTO;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldAttribute;
import df.base.persistence.repository.form.FieldAttributeRepository;
import df.base.service.CommonService;
import df.base.service.RedirectAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused"})
@Service
public class FieldAttributeService implements
        CommonService<FieldAttributeDTO, FieldAttribute, FieldAttributeRepository>, RedirectAware {

    @Autowired
    private FieldAttributeRepository repository;
    private String                   redirectUrl;

    @Transactional(readOnly = true)
    public List<FieldAttribute> getAllByField(Field field) {
        return repository.findAllByField(field);
    }

    public FieldAttribute createOrUpdate(FieldAttribute attribute, FieldAttributeDTO attributeDTO) {
        Optional<FieldAttribute> entity = getById(attributeDTO.id());

        if (entity.isPresent()) {
            return update(entity.get(), attributeDTO);
        } else {
            return create(attributeDTO);
        }
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
