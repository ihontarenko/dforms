package df.base.mapper.form;

import df.base.common.Mapper;
import df.base.persistence.entity.form.FieldConfig;
import df.base.dto.form.FormFieldConfigDTO;

public class FormFieldConfigMapper implements Mapper<FieldConfig, FormFieldConfigDTO> {

    @Override
    public FormFieldConfigDTO map(FieldConfig source) {
        FormFieldConfigDTO configDTO = new FormFieldConfigDTO();

        map(source, configDTO);

        return configDTO;
    }

    @Override
    public FieldConfig reverse(FormFieldConfigDTO source) {
        FieldConfig entity = new FieldConfig();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(FieldConfig source, FormFieldConfigDTO destination) {
        destination.setId(source.getId());
        destination.setConfigValue(source.getConfigValue());
        destination.setConfigName(source.getConfigName());
        destination.setFormFieldId(source.getField().getId());
    }

    @Override
    public void reverse(FormFieldConfigDTO source, FieldConfig destination) {
        destination.setConfigName(source.getConfigName());
        destination.setConfigValue(source.getConfigValue());
    }

}
