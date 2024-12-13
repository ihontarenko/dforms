package df.base.common.reflection;

import df.base.common.libs.jbm.scanner.ClassScanner;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.ClassMatchers;

import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * {@code ClassFinder} provides utility methods to locate classes based on specific criteria.
 * It supports searching for annotated classes, enumerations, and interface implementations within the provided base classes.
 * Results are cached to optimize repeated queries with the same parameters.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Find all classes annotated with @MyAnnotation
 * Set<Class<?>> annotatedClasses = ClassFinder.findAnnotatedClasses(MyAnnotation.class, BaseClass1.class, BaseClass2.class);
 *
 * // Find all enums within the specified base classes
 * Set<Class<?>> enums = ClassFinder.findEnums(BaseClass1.class, BaseClass2.class);
 *
 * // Find all implementations of MyInterface
 * Set<Class<?>> implementations = ClassFinder.findImplementations(MyInterface.class, BaseClass1.class, BaseClass2.class);
 * }</pre>
 */
public interface ClassFinder {

    /**
     * Cache to store the results of previous searches, keyed by the hash of the base classes.
     */
    Map<Integer, Set<Class<?>>> CACHE = new HashMap<>();

    /**
     * Default class scanner used for searching classes.
     */
    ClassScanner SCANNER = ClassScanner.getDefaultScanner();

    /**
     * Finds all classes annotated with the specified annotation within the provided base classes.
     *
     * @param annotation  the annotation to search for
     * @param baseClasses the base classes to restrict the search to
     * @return a set of classes annotated with the specified annotation
     */
    static Set<Class<?>> findAnnotatedClasses(Class<? extends Annotation> annotation, Class<?>... baseClasses) {
        return findAll(ClassMatchers.isAnnotatedWith(annotation), baseClasses);
    }

    /**
     * Finds all enumerations within the provided base classes.
     *
     * @param baseClasses the base classes to restrict the search to
     * @return a set of enumeration classes
     */
    static Set<Class<?>> findEnums(Class<?>... baseClasses) {
        return findAll(ClassMatchers.isEnum(), baseClasses);
    }

    /**
     * Finds all classes that implement the specified interface within the provided base classes.
     *
     * @param interfaceClass the interface to search for implementations
     * @param baseClasses    the base classes to restrict the search to
     * @return a set of classes that implement the specified interface
     */
    static Set<Class<?>> findImplementations(Class<?> interfaceClass, Class<?>... baseClasses) {
        return findAll(ClassMatchers.implementsInterface(interfaceClass).and(ClassMatchers.isAbstract().not()),
                       baseClasses);
    }

    /**
     * Finds all classes matching the given matcher within the provided base classes.
     *
     * @param matcher     a {@link Matcher} to filter classes
     * @param baseClasses the base classes to restrict the search to
     * @return a set of classes matching the criteria
     */
    static Set<Class<?>> findAll(Matcher<Class<?>> matcher, Class<?>... baseClasses) {
        return findAll(baseClasses).stream().filter(matcher::matches).collect(toSet());
    }

    /**
     * Finds all classes within the provided base classes without any filtering.
     *
     * @param baseClasses the base classes to restrict the search to
     * @return a set of all classes found within the specified base classes
     */
    static Set<Class<?>> findAll(Class<?>... baseClasses) {
        int cacheKey = Objects.hash(baseClasses);

        if (CACHE.containsKey(cacheKey)) {
            return CACHE.get(cacheKey);
        }

        Set<Class<?>> classes = new HashSet<>();

        SCANNER.setMatcher(Matcher.constant(true));

        for (Class<?> baseClass : baseClasses) {
            classes.addAll(SCANNER.scan(baseClass.getPackageName(), baseClass.getClassLoader()));
        }

        CACHE.put(cacheKey, classes);

        return classes;
    }
}
