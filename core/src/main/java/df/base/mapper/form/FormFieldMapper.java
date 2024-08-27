package df.base.mapper.form;

import df.base.jpa.form.ElementType;
import df.base.jpa.form.FieldStatus;
import df.base.jpa.form.FormField;
import df.base.common.Mapper;
import df.base.model.form.FormFieldDTO;

public class FormFieldMapper implements Mapper<FormField, FormFieldDTO> {

    @Override
    public FormFieldDTO map(FormField source) {
        FormFieldDTO fieldDTO = new FormFieldDTO();

        map(source, fieldDTO);

        return fieldDTO;
    }

    @Override
    public FormField reverse(FormFieldDTO source) {
        FormField entity = new FormField();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(FormField source, FormFieldDTO destination) {
        destination.setId(source.getId());
        destination.setElementType(source.getElementType().name());
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(source.getStatus().name());
    }

    @Override
    public void reverse(FormFieldDTO source, FormField destination) {
        destination.setElementType(ElementType.valueOf(source.getElementType()));
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(FieldStatus.valueOf(source.getStatus()));
    }

}
