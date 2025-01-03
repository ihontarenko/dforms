package df.application.mapping.reflection;

import df.application.Instances;
import df.application.dto.reflection.ClassDTO;
import df.common.mapping.Mapper;
import df.common.mapping.Mapping;
import df.common.matcher.Matcher;
import df.common.matcher.reflection.ClassMatchers;
import df.common.reflection.Reflections;
import df.application.dto.reflection.FieldDTO;
import df.application.dto.reflection.MethodDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static df.common.reflection.FieldFinder.getAllFields;
import static df.common.reflection.MethodFinder.getAllMethods;

public class ClassMapper implements Mapper<Class<?>, ClassDTO> {

    private static final Logger                  LOGGER    = LoggerFactory.getLogger(ClassMapper.class);
    private static final Map<Class<?>, ClassDTO> CACHE     = new HashMap<>();
    private static final Matcher<Class<?>>       IS_NATIVE = ClassMatchers.nameStarts("df.");
    private static final Matcher<Class<?>>       IS_JDK    = ClassMatchers.isJavaPackage();
    private static final Mapping                 MAPPING   = Instances.MAPPING;

    @Override
    public ClassDTO map(Class<?> rawClass) {
        Class<?> userClass = Reflections.getArrayClass(rawClass);
        ClassDTO classDTO  = CACHE.get(rawClass);

        if (classDTO == null) {
            // unwrap anonymous class til real class
            userClass = Reflections.getAnonymousClass(userClass);

            // mark DTO as array and unwrap it if it is one
            classDTO = new ClassDTO();
            classDTO.setArray(rawClass.isArray());

            // map base values Class<?> only
            mapClass(userClass, classDTO);

            // IMPORTANT! need to add class dto to cache
            // before mapping additional member to avoid StackOverflowError
            CACHE.put(rawClass, classDTO);

            // map class' members if class is from native package
            if (!classDTO.isForeign()) {
                mapSuperClasses(userClass, classDTO);
                mapInterfaces(userClass, classDTO);
                mapClassMembers(userClass, classDTO);
            }
        }

        return classDTO;
    }

    private void mapClass(Class<?> klass, ClassDTO classDTO) {
        classDTO.setName(klass.getSimpleName());
        classDTO.setFullName(klass.getName());
        classDTO.setNativeClass(klass);

        classDTO.setJdk(IS_JDK.matches(klass));
        classDTO.setForeign(!IS_NATIVE.matches(klass));
        classDTO.setPrimitive(klass.isPrimitive());
    }

    private void mapSuperClasses(Class<?> klass, ClassDTO classDTO) {
        for (Class<?> superClass : Reflections.getSuperClasses(klass)) {
            if (superClass != klass) {
                classDTO.addBaseClass(map(superClass));
            }
        }
    }

    private void mapInterfaces(Class<?> klass, ClassDTO classDTO) {
        for (Class<?> iface : Reflections.getClassInterfaces(klass)) {
            if (iface != klass) {
                classDTO.addInterface(map(iface));
            }
        }
    }

    private void mapClassMembers(Class<?> klass, ClassDTO classDTO) {
        try {
            for (Field field : getAllFields(klass, false)) {
                classDTO.addField((FieldDTO) MAPPING.mapper(field).map(field));
            }

            for (Method method : getAllMethods(klass, false)) {
                classDTO.addMethod((MethodDTO) MAPPING.mapper(method).map(method));
            }
        } catch (Exception exception) {
            LOGGER.error("Mapping class %s failed!".formatted(klass.getName()), exception);
        }
    }

    @Override
    public Class<?> reverse(ClassDTO klass) {
        throw new UnsupportedOperationException();
    }

}
