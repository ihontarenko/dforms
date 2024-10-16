package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.ClassDTO;

import static df.base.common.reflection.FieldFinder.getAllFields;
import static df.base.common.reflection.MethodFinder.getAllMethods;

public class ClassMapper implements Mapper<Class<?>, ClassDTO> {

    @Override
    public ClassDTO map(Class<?> source) {
        ClassDTO     classDTO     = new ClassDTO();
        MethodMapper methodMapper = new MethodMapper();
        FieldMapper  fieldMapper  = new FieldMapper();

        classDTO.setName(source.getName());
        classDTO.setNativeClass(source);

        try {
            getAllFields(source).stream().map(fieldMapper::map)
                    .forEach(classDTO::addField);
            getAllMethods(source).stream().map(methodMapper::map)
                    .forEach(classDTO::addMethod);
        } catch (NoClassDefFoundError ignore) {
            System.out.println(source);
            classDTO.setUndefinedDependencies(true);
        }

        return classDTO;
    }

    @Override
    public Class<?> reverse(ClassDTO source) {
        throw new UnsupportedOperationException();
    }

}
