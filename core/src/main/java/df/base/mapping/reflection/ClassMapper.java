package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.ClassMatchers;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.PackageDTO;
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
    private static final Matcher<Class<?>>       IS_NATIVE      = ClassMatchers.nameStarts("df.");
    private static final Matcher<Class<?>>       IS_JDK         = ClassMatchers.isJavaPackage();
    private static int cached = 0;
    private static int real = 0;

    @Override
    public ClassDTO map(Class<?> rawClass) {
        Class<?> classType = unwrap(rawClass);
        ClassDTO classDTO  = CACHE.get(rawClass);

        if (classDTO == null) {
            real++;
            // mark DTO as array and unwrap it if it is one
            classDTO = new ClassDTO();
            classDTO.setArray(rawClass.isArray());

            // map base values Class<?> only
            mapClass(classType, classDTO);

            // IMPORTANT! need to add class dto to cache
            // before mapping additional member to avoid StackOverflowError
            CACHE.put(rawClass, classDTO);

            mapClassPackage(classType, classDTO);

            // map class' members if class is from native package
            if (!classDTO.isForeign()) {
                mapClassMembers(classType, classDTO);
            }
        } else {
            cached++;
        }

        return classDTO;
    }

    private void mapClass(Class<?> source, ClassDTO classDTO) {
        classDTO.setShortName(source.getSimpleName());
        classDTO.setFullName(source.getName());
        classDTO.setNativeClass(source);

        classDTO.setJdk(IS_JDK.matches(source));
        classDTO.setForeign(!IS_NATIVE.matches(source));
        classDTO.setPrimitive(source.isPrimitive());
    }

    private void mapClassPackage(Class<?> source, ClassDTO classDTO) {
        PackageDTO packageDTO = new PackageDTO();

        if (!(source.isPrimitive() || source.isArray())) {
            packageDTO.setNativePackage(source.getPackage());
            packageDTO.setName(source.getPackage().getName());
        } else {
            packageDTO.setName(source.getName());
        }

        classDTO.setPackage(packageDTO);
    }

    private void mapClassMembers(Class<?> source, ClassDTO classDTO) {
        try {
            getAllFields(source, true).stream()
                    .map(FIELD_MAPPER::map).forEach(classDTO::addField);
            getAllMethods(source, true).stream()
                    .map(METHOD_MAPPER::map).forEach(classDTO::addMethod);
        } catch (NoClassDefFoundError noClassDefFoundError) {
            LOGGER.error("Mapping class %s failed!".formatted(source.getName()), noClassDefFoundError);
        }
    }

    public Class<?> unwrap(Class<?> clazz) {
        Class<?> unwrapped = clazz;

        if (clazz.isArray()) {
            unwrapped = clazz.getComponentType();
        }

        return unwrapped;
    }

    @Override
    public Class<?> reverse(ClassDTO source) {
        throw new UnsupportedOperationException();
    }

}
