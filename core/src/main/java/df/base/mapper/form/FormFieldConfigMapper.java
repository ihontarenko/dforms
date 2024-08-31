package df.base.mapper.form;

import df.base.internal.Mapper;
import df.base.jpa.form.FormFieldConfig;
import df.base.model.form.FormFieldConfigDTO;

public class FormFieldConfigMapper implements Mapper<FormFieldConfig, FormFieldConfigDTO> {

    @Override
    public FormFieldConfigDTO map(FormFieldConfig source) {
        FormFieldConfigDTO configDTO = new FormFieldConfigDTO();

        map(source, configDTO);

        return configDTO;
    }

    @Override
    public FormFieldConfig reverse(FormFieldConfigDTO source) {
        FormFieldConfig entity = new FormFieldConfig();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(FormFieldConfig source, FormFieldConfigDTO destination) {
        destination.setId(source.getId());
        destination.setConfigValue(source.getConfigValue());
        destination.setConfigName(source.getConfigName());
        destination.setFormFieldId(source.getFormField().getId());
    }

    @Override
    public void reverse(FormFieldConfigDTO source, FormFieldConfig destination) {
        destination.setConfigName(source.getConfigName());
        destination.setConfigValue(source.getConfigValue());
    }

}
