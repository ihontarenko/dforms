package df.base.common.reflection;

import df.base.common.matcher.reflection.FieldMatchers;
import df.base.common.matcher.reflection.MethodMatchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static df.base.common.matcher.reflection.MethodMatchers.hasParameterTypes;
import static df.base.common.matcher.reflection.MethodMatchers.isAbstract;

abstract public class Reflections {

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
    public static final String PROXY_CLASS_NAME_SEPARATOR = "$$";

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

    /**
     * Create class-type by it name with muffling ClassNotFoundException
     *
     * @param className the class name to retrieve class type
     * @return a class-type
     * @throws ReflectionException if creation of class type fails
     */
    public static Class<?> getClassFor(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Instantiates an object using the given constructor and arguments.
     *
     * @param <T> the type of object to instantiate
     * @param constructor the constructor to use for instantiation
     * @param arguments the arguments to pass to the constructor
     * @return an instance of the object
     * @throws ReflectionException if instantiation fails
     * @example
     * <pre>{@code
     * Constructor<MyClass> constructor = MyClass.class.getConstructor(String.class);
     * MyClass instance = Reflections.instantiate(constructor, "example");
     * }</pre>
     */
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

            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionException("COULD NOT INSTANTIATE OBJECT USING CONSTRUCTOR: " + constructor, e);
        }
    }

    /**
     * Finds the first constructor that matches the provided parameter types.
     *
     * @param clazz the class to search for constructors
     * @param types the parameter types of the desired constructor
     * @return the first matching constructor
     * @throws ReflectionException if no matching constructor is found
     * @see ConstructorFinder
     * @example
     * <pre>{@code
     * Constructor<?> constructor = Reflections.findFirstConstructor(MyClass.class, String.class, int.class);
     * }</pre>
     */
    public static Constructor<?> findFirstConstructor(Class<?> clazz, Class<?>... types) {
        return new ConstructorFinder().findFirst(clazz, hasParameterTypes(types).and(isAbstract().not()))
                .orElseThrow(() -> new ReflectionException("CONSTRUCTOR WITH (" + Arrays.toString(types) + ") PARAMETERS NOT FOUND"));
    }

    /**
     * Finds the first constructor annotated with the specified annotation.
     *
     * @param clazz the class to search for constructors
     * @param annotation the annotation to look for
     * @return the first annotated constructor
     * @throws ReflectionException if no annotated constructor is found
     * @see ConstructorFinder
     * @example
     * <pre>{@code
     * Constructor<?> constructor = Reflections.findFirstAnnotatedConstructor(MyClass.class, CustomAnnotation.class);
     * }</pre>
     */
    public static Constructor<?> findFirstAnnotatedConstructor(Class<?> clazz, Class<? extends Annotation> annotation) {
        return new ConstructorFinder().findFirst(clazz, MethodMatchers.isAnnotatedWith(annotation))
                .orElseThrow(() -> new ReflectionException("ANNOTATED CONSTRUCTOR NOT FOUND"));
    }

    /**
     * Finds all fields annotated with the specified annotation.
     *
     * @param clazz the class to search for fields
     * @param annotation the annotation to look for
     * @return a set of annotated fields
     * @see FieldFinder
     * @example
     * <pre>{@code
     * Set<Field> fields = Reflections.findAllAnnotatedFields(MyClass.class, CustomAnnotation.class);
     * }</pre>
     */
    public static Set<Field> findAllAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation) {
        return new HashSet<>(new FieldFinder().find(clazz, FieldMatchers.isAnnotatedWith(annotation)));
    }

    /**
     * Finds all methods annotated with the specified annotation.
     *
     * @param clazz the class to search for methods
     * @param annotation the annotation to look for
     * @return a set of annotated methods
     * @see MethodFinder
     * @example
     * <pre>{@code
     * Set<Method> methods = Reflections.findAllAnnotatedMethods(MyClass.class, CustomAnnotation.class);
     * }</pre>
     */
    public static Set<Method> findAllAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return new HashSet<>(new MethodFinder().find(clazz, MethodMatchers.isAnnotatedWith(annotation)));
    }

    /**
     * Finds a field by its name in the specified class.
     *
     * @param targetClass the class to search for the field
     * @param fieldName the name of the field
     * @return an optional field, or empty if the field is not found
     * @see FieldFinder
     * @example
     * <pre>{@code
     * Optional<Field> field = Reflections.getField(MyClass.class, "name");
     * }</pre>
     */
    public static Optional<Field> getField(Class<?> targetClass, String fieldName) {
        return new FieldFinder().findFirst(targetClass, FieldMatchers.nameEquals(fieldName));
    }

    /**
     * Finds all fields with the specified modifiers in the given class.
     *
     * @param type the class to search for fields
     * @param modifiers the modifiers to match (e.g., {@link java.lang.reflect.Modifier#PUBLIC})
     * @return a list of fields with the specified modifiers
     * @see FieldFinder
     * @example
     * <pre>{@code
     * List<Field> fields = Reflections.getClassFields(MyClass.class, Modifier.PUBLIC);
     * }</pre>
     */
    public static List<Field> getClassFields(Class<?> type, int modifiers) {
        return new FieldFinder().find(type, FieldMatchers.withModifier(modifiers));
    }

    /**
     * Sets the value of a field in an object.
     *
     * @param object the object whose field is to be set
     * @param fieldName the name of the field
     * @param value the value to set
     * @see #getFieldValue(Object, Field)
     * @example
     * <pre>{@code
     * Reflections.setFieldValue(myObject, "name", "John Doe");
     * }</pre>
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        getField(object.getClass(), fieldName)
                .ifPresent(field -> setFieldValue(object, field, value));
    }

    /**
     * Sets the value of a field in an object.
     *
     * @param object the object whose field is to be set
     * @param field the field to set
     * @param value the value to set
     */
    public static void setFieldValue(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException ignore) {
        }
    }

    /**
     * Gets the value of a field from an object.
     *
     * @param object the object whose field value is to be retrieved
     * @param fieldName the name of the field
     * @return the value of the field
     * @see #setFieldValue(Object, String, Object)
     * @example
     * <pre>{@code
     * Object value = Reflections.getFieldValue(myObject, "name");
     * }</pre>
     */
    public static Object getFieldValue(Object object, String fieldName) {
        return getField(object.getClass(), fieldName)
                .map(field -> getFieldValue(object, field))
                .orElse(null);
    }

    /**
     * Gets the value of a field from an object.
     *
     * @param object the object whose field value is to be retrieved
     * @param field the field to retrieve the value from
     * @return the value of the field
     */
    public static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ignore) {
            return null;
        }
    }

    /**
     * Invokes a method on the specified object with the given arguments.
     *
     * @param object the object to invoke the method on
     * @param method the method to invoke
     * @param arguments the arguments to pass to the method
     * @return the result of the method invocation
     * @see Method#invoke(Object, Object...)
     * @example
     * <pre>{@code
     * Method method = MyClass.class.getMethod("sayHello");
     * Reflections.invokeMethod(myObject, method);
     * }</pre>
     */
    public static Object invokeMethod(Object object, Method method, Object... arguments) {
        try {
            method.setAccessible(true);
            return method.invoke(object, arguments);
        } catch (Throwable e) {
            Throwable targetException = e;

            if (e instanceof InvocationTargetException invocationTargetException) {
                targetException = invocationTargetException.getTargetException();
            }

            throw new ReflectionException(targetException.getMessage(), targetException);
        }
    }

    /**
     * Invokes a getter method on the specified object.
     *
     * @param object the object to invoke the getter on
     * @param methodName the name of the getter method
     * @return the result of the getter method, or null if not found
     */
    public static Object getMethodValue(Object object, String methodName) {
        return getGetter(object.getClass(), methodName)
                .map(method -> invokeMethod(object, method))
                .orElse(null);
    }

    /**
     * Finds a method in the specified class by name and parameter types.
     *
     * @param targetClass the class to search for the method
     * @param methodName the name of the method
     * @param types the parameter types of the method
     * @return an optional method, or empty if not found
     * @see MethodFinder
     * @example
     * <pre>{@code
     * Optional<Method> method = Reflections.getMethod(MyClass.class, "sayHello", String.class);
     * }</pre>
     */
    public static Optional<Method> getMethod(Class<?> targetClass, String methodName, Class<?>... types) {
        return new MethodFinder().findFirst(targetClass, MethodMatchers.nameEquals(methodName).and(
                hasParameterTypes(types)));
    }

    /**
     * Finds a setter method in the specified class by the field name.
     *
     * @param targetClass the class to search for the setter method
     * @param methodName the field name (the setter will be "set" + methodName)
     * @return an optional setter method, or empty if not found
     */
    public static Optional<Method> getSetter(Class<?> targetClass, String methodName) {
        return getMethod(targetClass,
                "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
    }

    /**
     * Finds a getter method in the specified class by the field name.
     *
     * @param targetClass the class to search for the getter method
     * @param methodName the field name (the getter will be "get" + methodName)
     * @return an optional getter method, or empty if not found
     */
    public static Optional<Method> getGetter(Class<?> targetClass, String methodName) {
        return getMethod(targetClass,
                "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
    }

    /**
     * Finds all public methods in the specified class.
     *
     * @param clazz the class to search for methods
     * @return a list of public methods
     * @see MethodFinder
     * @example
     * <pre>{@code
     * List<Method> methods = Reflections.getClassMethods(MyClass.class);
     * }</pre>
     */
    public static List<Method> getClassMethods(Class<?> clazz) {
        return new MethodFinder().find(clazz, MethodMatchers.isPublic());
    }

    /**
     * Checks if the specified class contains a method with the given name.
     *
     * @param targetClass the class to search for the method
     * @param methodName the name of the method
     * @return true if the method exists, false otherwise
     */
    public static boolean hasMethod(Class<?> targetClass, String methodName) {
        return getMethod(targetClass, methodName).isPresent();
    }

    /**
     * Checks if the specified method is an "equals" method.
     *
     * @param method the method to check
     * @return true if the method is "equals", false otherwise
     */
    public static boolean isEqualsMethod(Method method) {
        return method != null && "equals".equals(method.getName()) && method.getParameterCount() == 1;
    }

    /**
     * Checks if the specified method is a "hashCode" method.
     *
     * @param method the method to check
     * @return true if the method is "hashCode", false otherwise
     */
    public static boolean isHashCodeMethod(Method method) {
        return method != null && "hashCode".equals(method.getName()) && method.getParameterCount() == 0;
    }

    /**
     * Unwraps the actual class from a proxy class.
     *
     * @param classType the class to unwrap
     * @return the unwrapped class, or the same class if not a proxy
     */
    public static Class<?> unwrapProxyClass(Class<?> classType) {
        if (classType.getName().contains(PROXY_CLASS_NAME_SEPARATOR)) {
            Class<?> superClass = classType.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                return superClass;
            }
        }
        return classType;
    }

    /**
     * Retrieves all interfaces implemented by the given class and its superclasses.
     *
     * @param baseClass the class to retrieve interfaces for
     * @return an array of implemented interfaces
     */
    public static Class<?>[] getClassInterfaces(Class<?> baseClass) {
        Set<Class<?>> interfaces = new HashSet<>();

        while (baseClass != null) {
            interfaces.addAll(Set.of(baseClass.getInterfaces()));
            baseClass = baseClass.getSuperclass();
        }

        return interfaces.toArray(Class[]::new);
    }

    /**
     * Convert object array to corresponding types
     *
     * @param arguments that be converted
     * @return an array of object types
     */
    public static Class<?>[] getArgumentsTypes(Object... arguments) {
        Class<?>[] argumentTypes = new Class<?>[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            argumentTypes[i] = arguments[i] != null ? arguments[i].getClass() : Object.class;
        }

        return argumentTypes;
    }

    /**
     * Create method name with declared class name
     *
     * @param method to retrieve that name
     * @return method name with class name
     */
    public static String getMethodName(Method method) {
        return "%s#%s".formatted(method.getDeclaringClass().getSimpleName(), method.getName());
    }

}
