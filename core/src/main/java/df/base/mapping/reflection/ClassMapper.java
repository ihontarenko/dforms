package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.ClassDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static df.base.common.reflection.FieldFinder.getAllFields;
import static df.base.common.reflection.MethodFinder.getAllMethods;

public class ClassMapper implements Mapper<Class<?>, ClassDTO> {

    private static final Map<Class<?>, ClassDTO> MAPPED_CLASSES = new HashMap<>();

    @Override
    public ClassDTO map(Class<?> source) {

        if (MAPPED_CLASSES.containsKey(source)) {
            return MAPPED_CLASSES.get(source);
        }

        ClassDTO     classDTO     = new ClassDTO();
        MethodMapper methodMapper = new MethodMapper();
        FieldMapper  fieldMapper  = new FieldMapper();

        classDTO.setShortName(source.getSimpleName());
        classDTO.setFullName(source.getName());
        classDTO.setNativeClass(source);

        MAPPED_CLASSES.put(source, classDTO);

        try {
            getAllFields(source, true).stream().map(fieldMapper::map)
                    .forEach(classDTO::addField);
            getAllMethods(source, true).stream().map(methodMapper::map)
                    .forEach(classDTO::addMethod);
        } catch (NoClassDefFoundError ignore) {
            classDTO.setUndefinedDependencies(true);
        }

        return classDTO;
    }

    @Override
    public Class<?> reverse(ClassDTO source) {
        throw new UnsupportedOperationException();
    }

}
