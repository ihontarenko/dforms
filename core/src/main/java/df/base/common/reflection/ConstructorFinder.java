package df.base.common.reflection;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that finds constructors in a given class. It supports scanning superclasses
 * to retrieve inherited constructors if required.
 *
 * @example
 * <pre>{@code
 * ConstructorFinder finder = new ConstructorFinder();
 * finder.find(SomeClass.class, constructor -> true, context)
 *      .forEach(constructor -> System.out.println(constructor));
 * }</pre>
 */
public class ConstructorFinder extends AbstractFinder<Constructor<?>> {

    /**
     * Retrieves all constructors from the specified class.
     *
     * @param clazz the class whose constructors are to be retrieved
     * @return a collection of constructors from the class
     */
    @Override
    protected Collection<Constructor<?>> getMembers(Class<?> clazz) {
        return getAllConstructors(clazz);
    }

    /**
     * Retrieves all constructors from the specified class without scanning superclasses.
     *
     * @param clazz the class whose constructors are to be retrieved
     * @return a collection of constructors from the class
     */
    public static Collection<Constructor<?>> getAllConstructors(Class<?> clazz) {
        return getAllConstructors(clazz, false);
    }

    /**
     * Retrieves all constructors from the specified class. If {@code scanSuperclasses} is true,
     * it recursively scans all superclasses to retrieve inherited constructors.
     *
     * @param clazz            the class whose constructors are to be retrieved
     * @param scanSuperclasses whether to scan superclasses for constructors
     * @return a collection of constructors from the class and optionally from its superclasses
     */
    public static Collection<Constructor<?>> getAllConstructors(Class<?> clazz, boolean scanSuperclasses) {
        Set<Constructor<?>> constructors = new HashSet<>(Set.of(clazz.getDeclaredConstructors()));

        // Optionally scan superclasses to include their constructors
        if (scanSuperclasses && clazz.getSuperclass() != null) {
            constructors.addAll(getAllConstructors(clazz.getSuperclass(), true));
        }

        return constructors;
    }

}
