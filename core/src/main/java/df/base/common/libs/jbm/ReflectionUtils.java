package df.base.common.libs.jbm;

import df.base.common.libs.jbm.bean.ObjectCreationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static java.util.Objects.requireNonNull;

abstract public class ReflectionUtils {

    private static final Map<Class<?>, Object> PRIMITIVES_DEFAULT_TYPE_VALUES = Map.of(
            boolean.class, false,
            byte.class, (byte) 0,
            short.class, (short) 0,
            int.class, 0,
            long.class, 0L,
            float.class, 0F,
            double.class, 0D,
            char.class, '\0'
    );

    public static <T> T instantiate(Constructor<T> constructor, Object... arguments) {
        final int parametersCount = constructor.getParameterCount();
        T         instance;

        try {
            constructor.setAccessible(true);

            if (parametersCount == 0) {
                instance = constructor.newInstance();
            } else {
                Object[]   constructorArguments = new Object[arguments.length];
                Class<?>[] parameterTypes       = constructor.getParameterTypes();

                for (int i = 0; i < constructorArguments.length; i++) {
                    if (arguments[i] == null) {
                        Class<?> parameterType = parameterTypes[i];
                        constructorArguments[i] = parameterType.isPrimitive()
                                ? PRIMITIVES_DEFAULT_TYPE_VALUES.get(parameterType) : null;
                    } else {
                        constructorArguments[i] = arguments[i];
                    }
                }

                instance = constructor.newInstance(constructorArguments);
            }
        } catch (Exception e) {
            throw new ObjectCreationException("UNABLE TO CREATE INSTANCE OF: " + constructor.getDeclaringClass().getName(), e);
        }

        return instance;
    }

    public static Set<Field> findAllAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Field> fields = new HashSet<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }

            if (field.isAnnotationPresent(annotation)) {
                fields.add(field);
            }
        }

        if (clazz.getSuperclass() != null) {
            fields.addAll(findAllAnnotatedFields(clazz.getSuperclass(), annotation));
        }

        return fields;
    }

    public static Set<Method> findAllAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Method> methods = new HashSet<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }

        if (clazz.getSuperclass() != null) {
            methods.addAll(findAllAnnotatedMethods(clazz.getSuperclass(), annotation));
        }

        return methods;
    }

    public static Constructor<?> findFirstConstructor(Class<?> clazz, Class<?>... types) {
        Constructor<?> constructor = null;

        try {
            constructor = clazz.getDeclaredConstructor(types);
        } catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() != null && !Object.class.isAssignableFrom(clazz.getSuperclass())) {
                constructor = findFirstConstructor(clazz.getSuperclass(), types);
            }
        }

        if (constructor == null) {
            throw new ObjectCreationException(clazz.getName() + ": CONSTRUCTOR WITH (" + Arrays.toString(types) + ") PARAMETERS NOT FOUND");
        }

        return constructor;
    }

    public static Constructor<?> findFirstAnnotatedConstructor(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Constructor<?>>      constructors = findAllAnnotatedConstructors(clazz, annotation);
        Iterator<Constructor<?>> iterator     = constructors.iterator();

        if (!iterator.hasNext()) {
            throw new ObjectCreationException("CANNOT FIND APPROPRIATE ANNOTATED CONSTRUCTOR FOR CLASS: " + clazz.getName() + " WITH @" + annotation.getName());
        }

        return iterator.next();
    }

    public static Set<Constructor<?>> findAllAnnotatedConstructors(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Constructor<?>> constructors = new HashSet<>();

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(annotation)) {
                constructors.add(constructor);
            }
        }

        if (clazz.getSuperclass() != null) {
            constructors.addAll(findAllAnnotatedConstructors(clazz.getSuperclass(), annotation));
        }

        return constructors;
    }

    public static Object invokeMethod(Object object, Method method, Object... arguments) {
        Object value = null;

        try {
            method.setAccessible(true);
            value = method.invoke(object, arguments);
        } catch (IllegalAccessException | InvocationTargetException ignore) {
        }

        return value;
    }

    public static List<Field> getObjectField(Object object, int modifiers) {
        return getClassField(requireNonNull(object).getClass(), modifiers);
    }

    public static List<Field> getClassField(Class<?> type, int modifiers) {
        List<Field> fields = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            if ((field.getModifiers() & modifiers) == 0) {
                fields.add(field);
            }
        }

        return fields;
    }

    public static Optional<Field> getField(Class<?> targetClass, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return Optional.of(field);
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }

    public static Optional<Method> getMethod(Class<?> targetClass, String methodName) {
        try {
            Method method = targetClass.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return Optional.ofNullable(method);
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    public static Object getMethodValue(Object object, Method method) {
        Object value = null;

        try {
            method.setAccessible(true);
            value = method.invoke(object);
        } catch (InvocationTargetException | IllegalAccessException ignore) { }

        return value;
    }

    public static Object getMethodValue(Object object, String methodName) {
        return getGetter(object.getClass(), methodName)
                .map(method -> getMethodValue(object, method))
                .orElse(null);
    }

    public static Optional<Method> getSetter(Class<?> targetClass, String methodName) {
        return getMethod(targetClass,
                "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
    }

    public static Optional<Method> getGetter(Class<?> targetClass, String methodName) {
        return getMethod(targetClass,
                "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
    }

    public static void setFieldValue(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException ignore) {
        }
    }

    public static Object getFieldValue(Object object, Field field) {
        Object value = null;

        try {
            field.setAccessible(true);
            value = field.get(object);
        } catch (IllegalAccessException ignore) {
        }

        return value;
    }

    public static Object getFieldValue(Object object, String fieldName) {
        return getField(object.getClass(), fieldName)
                .map(field -> getFieldValue(object, field))
                .orElse(null);
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        getField(object.getClass(), fieldName)
                .ifPresent(field -> setFieldValue(object, field, value));
    }

    public static List<Method> extractStaticMethods(Class<?> clazz) {
        Method[]     methods       = clazz.getMethods();
        List<Method> staticMethods = new ArrayList<>(methods.length);

        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                staticMethods.add(method);
            }
        }

        return staticMethods;
    }

}
