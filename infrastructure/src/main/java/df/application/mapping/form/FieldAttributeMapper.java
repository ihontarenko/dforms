package df.application.mapping.form;

import org.jmouse.common.mapping.Mapper;
import df.application.dto.form.FieldAttributeDTO;
import df.application.persistence.entity.form.FieldAttribute;
import org.springframework.stereotype.Service;

@Service
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
        destination.setKey(source.getName());
        destination.setFieldId(source.getField().getId());
    }

    @Override
    public void reverse(FieldAttributeDTO source, FieldAttribute destination) {
        destination.setName(source.getKey());
        destination.setValue(source.getValue());
    }

}
