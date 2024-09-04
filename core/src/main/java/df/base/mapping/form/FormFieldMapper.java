package df.base.mapping.form;

import df.base.persistence.entity.support.ElementType;
import df.base.persistence.entity.support.FieldStatus;
import df.base.persistence.entity.form.Field;
import df.base.common.Mapper;
import df.base.persistence.entity.support.UsageType;
import df.base.dto.form.FormFieldDTO;

public class FormFieldMapper implements Mapper<Field, FormFieldDTO> {

    @Override
    public FormFieldDTO map(Field source) {
        FormFieldDTO fieldDTO = new FormFieldDTO();

        map(source, fieldDTO);

        return fieldDTO;
    }

    @Override
    public Field reverse(FormFieldDTO source) {
        Field entity = new Field();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(Field source, FormFieldDTO destination) {
        destination.setId(source.getId());
        destination.setUsageType(source.getUsageType().name());
        destination.setElementType(source.getElementType().name());
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(source.getStatus().name());
    }

    @Override
    public void reverse(FormFieldDTO source, Field destination) {
        destination.setUsageType(UsageType.valueOf(source.getUsageType()));
        destination.setElementType(ElementType.valueOf(source.getElementType()));
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(FieldStatus.valueOf(source.getStatus()));
    }

}
