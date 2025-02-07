package df.application.mapping.reflection;

import df.application.Instances;
import org.jmouse.common.mapping.Mapper;
import df.application.dto.reflection.FieldDTO;

import java.lang.reflect.Field;

public class FieldMapper implements Mapper<Field, FieldDTO> {

    @Override
    public FieldDTO map(Field source) {
        FieldDTO fieldDTO = new FieldDTO();

        fieldDTO.setName(source.getName());
        fieldDTO.setType(Instances.MAPPING.map(source.getType()));
        fieldDTO.setNativeField(source);
        fieldDTO.setNativeClass(source.getDeclaringClass());

        return fieldDTO;
    }

    @Override
    public Field reverse(FieldDTO source) {
        throw new UnsupportedOperationException();
    }

}
