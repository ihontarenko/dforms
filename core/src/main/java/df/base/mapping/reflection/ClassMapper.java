package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.ClassMatchers;
import df.base.dto.reflection.ClassDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static df.base.common.reflection.FieldFinder.getAllFields;
import static df.base.common.reflection.MethodFinder.getAllMethods;

public class ClassMapper implements Mapper<Class<?>, ClassDTO> {

    private static final Logger                  LOGGER         = LoggerFactory.getLogger(ClassMapper.class);
    private static final Map<Class<?>, ClassDTO> CACHE          = new HashMap<>();
    private static final MethodMapper            METHOD_MAPPER  = new MethodMapper();
    private static final FieldMapper             FIELD_MAPPER   = new FieldMapper();
    private static final PackageMapper           PACKAGE_MAPPER = new PackageMapper();
    private static final Matcher<Class<?>>       CLASS_MATCHER  = ClassMatchers.nameStarts("df.");

    @Override
    public ClassDTO map(Class<?> source) {

        if (CACHE.containsKey(source)) {
            return CACHE.get(source);
        }

        ClassDTO classDTO = new ClassDTO();

        classDTO.setShortName(source.getSimpleName());
        classDTO.setFullName(source.getName());
        classDTO.setNativeClass(source);
        classDTO.setArray(source.isArray());

        if (source.isPrimitive() || source.isArray()) {
            classDTO.setPackage(PACKAGE_MAPPER.map(source.getName()));
        } else {
            classDTO.setPackage(PACKAGE_MAPPER.map(source.getPackage()));
        }

        if (!CLASS_MATCHER.matches(source)) {
            System.out.println(source.getName());
        }

        CACHE.put(source, classDTO);

        try {
            // todo: consider to create isJavaPackage matcher
            getAllFields(source, true).stream()
                    .map(FIELD_MAPPER::map).forEach(classDTO::addField);
            getAllMethods(source, true).stream()
                    .map(METHOD_MAPPER::map).forEach(classDTO::addMethod);
        } catch (NoClassDefFoundError noClassDefFoundError) {
            LOGGER.error("Mapping class %s failed!".formatted(source.getName()), noClassDefFoundError);
        }

        return classDTO;
    }

    @Override
    public Class<?> reverse(ClassDTO source) {
        throw new UnsupportedOperationException();
    }

}
