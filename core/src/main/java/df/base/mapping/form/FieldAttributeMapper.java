package df.base.mapping.form;

import df.base.common.Mapper;
import df.base.dto.form.FieldAttributeDTO;
import df.base.persistence.entity.form.FieldAttribute;

public class FieldAttributeMapper implements Mapper<FieldAttribute, FieldAttributeDTO> {

    @Override
    public FieldAttributeDTO map(FieldAttribute source) {
        FieldAttributeDTO configDTO = new FieldAttributeDTO();

        map(source, configDTO);

        return configDTO;
    }

    @Override
    public FieldAttribute reverse(FieldAttributeDTO source) {
        FieldAttribute entity = new FieldAttribute();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(FieldAttribute source, FieldAttributeDTO destination) {
        destination.setId(source.getId());
        destination.setValue(source.getValue());
        destination.setName(source.getName());
        destination.setFieldId(source.getField().getId());
    }

    @Override
    public void reverse(FieldAttributeDTO source, FieldAttribute destination) {
        destination.setName(source.getName());
        destination.setValue(source.getValue());
    }

}
