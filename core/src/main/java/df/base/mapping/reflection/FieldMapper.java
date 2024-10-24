package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.FieldDTO;

import java.lang.reflect.Field;

public class FieldMapper implements Mapper<Field, FieldDTO> {

    private final ClassMapper classMapper = new ClassMapper();

    @Override
    public FieldDTO map(Field source) {
        FieldDTO fieldDTO = new FieldDTO();

        fieldDTO.setName(source.getName());
        fieldDTO.setType(classMapper.map(source.getType()));
        fieldDTO.setNativeField(source);
        fieldDTO.setNativeClass(source.getDeclaringClass());

        return fieldDTO;
    }

    @Override
    public Field reverse(FieldDTO source) {
        throw new UnsupportedOperationException();
    }

}
