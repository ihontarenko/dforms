package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.common.reflection.FieldFinder;
import df.base.common.reflection.MethodFinder;
import df.base.dto.reflection.ClassDTO;

public class ClassMapper implements Mapper<Class<?>, ClassDTO> {

    @Override
    public ClassDTO map(Class<?> source) {
        ClassDTO     classDTO     = new ClassDTO();
        MethodMapper methodMapper = new MethodMapper();
        FieldMapper  fieldMapper  = new FieldMapper();

        classDTO.setName(source.getName());
        MethodFinder.getAllMethods(source).stream()
                .map(methodMapper::map).forEach(classDTO::addMethod);
        FieldFinder.getAllFields(source).stream()
                .map(fieldMapper::map).forEach(classDTO::addField);
        classDTO.setNativeClass(source);

        return classDTO;
    }

    @Override
    public Class<?> reverse(ClassDTO source) {
        throw new UnsupportedOperationException();
    }

}
