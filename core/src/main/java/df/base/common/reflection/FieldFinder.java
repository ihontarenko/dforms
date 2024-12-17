package df.base.common.reflection;

import df.base.common.matcher.Matcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static df.base.common.matcher.reflection.FieldMatchers.isAnnotatedWith;

/**
 * A class that finds fields in a given class. It supports scanning superclasses
 * to retrieve inherited fields and filtering fields based on annotations.
 */
public class FieldFinder extends AbstractFinder<Field> {

    /**
     * Retrieves all fields from the given class that are annotated with any of the specified annotations.
     *
     * @param clazz       the class whose fields are to be searched
     * @param annotations the annotations to filter fields by
     * @return an array of fields that are annotated with any of the given annotations
     * @example
     * <pre>{@code
     * // Example: Finding all fields annotated with @Deprecated or @NotNull
     * Field[] annotatedFields = FieldFinder.getAnnotatedWith(SomeClass.class, Deprecated.class, NotNull.class);
     * for (Field field : annotatedFields) {
     *     System.out.println(field.getName());
     * }
     * }</pre>
     */
    @SafeVarargs
    public static Field[] getAnnotatedWith(Class<?> clazz, Class<? extends Annotation>... annotations) {
        MemberFinder<Field> finder  = new FieldFinder();
        Matcher<Field>      matcher = Matcher.constant(false);

        // Combine matchers for each annotation
        for (Class<? extends Annotation> annotation : annotations) {
            matcher = matcher.or(isAnnotatedWith(annotation));
        }

        // Find and return fields matching any of the annotations
        return finder.find(clazz, matcher).toArray(Field[]::new);
    }

    /**
     * Retrieves all fields from the specified class, with scanning superclasses.
     *
     * @param clazz the class whose fields are to be retrieved
     * @return a collection of fields from the class
     * @example
     * <pre>{@code
     * // Example: Finding all fields from a class
     * Collection<Field> fields = FieldFinder.getAllFields(SomeClass.class);
     * fields.forEach(field -> System.out.println(field.getName()));
     * }</pre>
     */
    public static Collection<Field> getAllFields(Class<?> clazz) {
        return getAllFields(clazz, true);
    }

    /**
     * Retrieves all fields from the specified class, and optionally scans the superclasses
     * to retrieve inherited fields.
     *
     * @param clazz     the class whose fields are to be retrieved
     * @param deepScan  whether to scan superclasses for fields
     * @return a collection of fields from the class and, optionally, its superclasses
     * @example
     * <pre>{@code
     * // Example: Retrieving fields from a class and its superclasses
     * Collection<Field> fields = FieldFinder.getAllFields(SomeClass.class, true);
     * fields.forEach(field -> System.out.println(field.getName()));
     * }</pre>
     */
    public static Collection<Field> getAllFields(Class<?> clazz, boolean deepScan) {
        Set<Field> fields = new HashSet<>(Set.of(clazz.getDeclaredFields()));

        // Optionally scan superclasses for fields
        if (deepScan && clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass(), true));
        }

        return fields;
    }

    /**
     * Retrieves all fields from the specified class, including its superclasses.
     *
     * @param clazz the class whose fields are to be retrieved
     * @return a collection of fields from the class and its superclasses
     */
    @Override
    protected Collection<Field> getMembers(Class<?> clazz) {
        return getAllFields(clazz);
    }

    /**
     * Retrieves all fields from the specified class, including its superclasses.
     *
     * @param clazz the class whose fields are to be retrieved
     * @param deepScan whether to scan superclasses for fields
     * @return a collection of fields from the class and its superclasses
     */
    @Override
    protected Collection<Field> getMembers(Class<?> clazz, boolean deepScan) {
        return getAllFields(clazz, deepScan);
    }

    /**
     * Returns a {@link FieldFilter} to apply additional filtering criteria to fields of the specified class.
     *
     * @param clazz the class to filter fields from
     * @return a {@link FieldFilter} instance for filtering fields
     */
    @Override
    public FieldFilter filter(Class<?> clazz) {
        return new FieldFilter(this, Matcher.constant(true), clazz);
    }
}
