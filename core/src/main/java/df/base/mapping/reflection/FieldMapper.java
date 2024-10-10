package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.FieldDTO;

import java.lang.reflect.Field;

public class FieldMapper implements Mapper<Field, FieldDTO> {

    @Override
    public FieldDTO map(Field source) {
        FieldDTO fieldDTO = new FieldDTO();

        fieldDTO.setName(source.getName());
        fieldDTO.setNativeField(source);
        fieldDTO.setNativeClass(source.getDeclaringClass());

        return fieldDTO;
    }

    @Override
    public Field reverse(FieldDTO source) {
        throw new UnsupportedOperationException();
    }

}
