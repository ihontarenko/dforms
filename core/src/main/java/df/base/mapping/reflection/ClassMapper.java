package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.PackageDTO;

import java.util.HashMap;
import java.util.Map;

import static df.base.common.reflection.FieldFinder.getAllFields;
import static df.base.common.reflection.MethodFinder.getAllMethods;

public class ClassMapper implements Mapper<Class<?>, ClassDTO> {

    private static final Map<Class<?>, ClassDTO> MAPPED_CLASSES    = new HashMap<>();
    private static final MethodMapper            METHOD_MAPPER     = new MethodMapper();
    private static final FieldMapper             FIELD_MAPPER      = new FieldMapper();
    private static final PackageMapper           PACKAGE_MAPPER    = new PackageMapper();

    @Override
    public ClassDTO map(Class<?> source) {

        if (MAPPED_CLASSES.containsKey(source)) {
            return MAPPED_CLASSES.get(source);
        }

        ClassDTO classDTO = new ClassDTO();

        classDTO.setShortName(source.getSimpleName());
        classDTO.setFullName(source.getName());
        classDTO.setNativeClass(source);

        if (source.isPrimitive() || source.isArray()) {
            classDTO.setPackage(new PackageDTO() {{
                setName(source.getSimpleName());
            }});
        } else {
            classDTO.setPackage(PACKAGE_MAPPER.map(source.getPackage()));
        }

        MAPPED_CLASSES.put(source, classDTO);

        try {
            getAllFields(source, true).stream().map(FIELD_MAPPER::map)
                    .forEach(classDTO::addField);
            getAllMethods(source, true).stream().map(METHOD_MAPPER::map)
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
