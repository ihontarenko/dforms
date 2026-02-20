package df.application.old_mapping.form;

import df.application.dto.form.FieldDTO;
import org.jmouse.common.mapping.Mapper;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.support.ElementType;
import df.application.persistence.entity.support.FieldStatus;
import df.application.persistence.entity.support.UsageType;
import org.springframework.stereotype.Service;

@Service
public class FieldMapper implements Mapper<Field, FieldDTO> {

    @Override
    public FieldDTO map(Field source) {
        FieldDTO fieldDTO = new FieldDTO();

        map(source, fieldDTO);

        return fieldDTO;
    }

    @Override
    public Field reverse(FieldDTO source) {
        Field entity = new Field();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(Field source, FieldDTO destination) {
        destination.setId(source.getId());
        destination.setUsageType(source.getUsageType().name());
        destination.setElementType(source.getElementType().name());
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(source.getStatus().name());
    }

    @Override
    public void reverse(FieldDTO source, Field destination) {
        destination.setUsageType(UsageType.valueOf(source.getUsageType()));
        destination.setElementType(ElementType.valueOf(source.getElementType()));
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(FieldStatus.valueOf(source.getStatus()));
    }

}
