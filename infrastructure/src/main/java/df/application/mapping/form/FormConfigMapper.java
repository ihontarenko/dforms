package df.application.mapping.form;

import df.application.dto.form.FormConfigDTO;
import org.jmouse.core.mapping.Mapper;
import df.application.persistence.entity.form.FormConfig;
import org.springframework.stereotype.Service;

@Service
public class FormConfigMapper implements Mapper<FormConfig, FormConfigDTO> {

    @Override
    public FormConfigDTO map(FormConfig source) {
        FormConfigDTO configDTO = new FormConfigDTO();

        map(source, configDTO);

        return configDTO;
    }

    @Override
    public FormConfig reverse(FormConfigDTO source) {
        FormConfig entity = new FormConfig();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(FormConfig source, FormConfigDTO destination) {
        destination.setId(source.getId());
        destination.setConfigName(source.getConfigName());
        destination.setConfigValue(source.getConfigValue());
        destination.setFormId(source.getForm().getId());
    }

    @Override
    public void reverse(FormConfigDTO source, FormConfig destination) {
        destination.setConfigName(source.getConfigName());
        destination.setConfigValue(source.getConfigValue());
    }

}
