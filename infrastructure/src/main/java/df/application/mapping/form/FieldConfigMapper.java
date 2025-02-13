package df.application.mapping.form;

import df.application.dto.form.FieldConfigDTO;
import org.jmouse.common.mapping.Mapper;
import df.application.persistence.entity.form.FieldConfig;
import org.springframework.stereotype.Service;

@Service
public class FieldConfigMapper implements Mapper<FieldConfig, FieldConfigDTO> {

    @Override
    public FieldConfigDTO map(FieldConfig source) {
        FieldConfigDTO configDTO = new FieldConfigDTO();

        map(source, configDTO);

        return configDTO;
    }

    @Override
    public FieldConfig reverse(FieldConfigDTO source) {
        FieldConfig entity = new FieldConfig();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(FieldConfig source, FieldConfigDTO destination) {
        destination.setId(source.getId());
        destination.setValue(source.getConfigValue());
        destination.setKey(source.getConfigName());
        destination.setFieldId(source.getField().getId());
    }

    @Override
    public void reverse(FieldConfigDTO source, FieldConfig destination) {
        destination.setConfigName(source.getKey());
        destination.setConfigValue(source.getValue());
    }

}
