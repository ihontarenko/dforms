package df.base.service.form;

import df.base.dto.form.FieldOptionDTO;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldOption;
import df.base.persistence.repository.form.FieldOptionRepository;
import df.base.service.CommonService;
import df.base.service.RedirectAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings({"unused"})
@Service
public class FieldOptionService implements CommonService<FieldOptionDTO, FieldOption, FieldOptionRepository>, RedirectAware {

    @Autowired
    private FieldOptionRepository repository;
    private String                redirectUrl;

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
