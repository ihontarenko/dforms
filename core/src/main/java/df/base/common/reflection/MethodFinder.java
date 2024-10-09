package df.base.common.reflection;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that finds methods in a given class. It supports scanning superclasses
 * and interfaces to retrieve inherited or implemented methods.
 */
public class MethodFinder extends AbstractFinder<Method> {

    /**
     * Retrieves all methods from the specified class.
     *
     * @param clazz the class whose methods are to be retrieved
     * @return a collection of methods from the class
     */
    @Override
    protected Collection<Method> getMembers(Class<?> clazz) {
        return getAllMethods(clazz);
    }

    /**
     * Retrieves all methods from the specified class, without scanning superclasses or interfaces.
     *
     * @param clazz the class whose methods are to be retrieved
     * @return a collection of methods from the class
     */
    public static Collection<Method> getAllMethods(Class<?> clazz) {
        return getAllMethods(clazz, false);
    }

    /**
     * Retrieves all methods from the specified class. If {@code withSuperclass} is true,
     * it recursively scans all superclasses and implemented interfaces to retrieve inherited methods.
     *
     * @param clazz           the class whose methods are to be retrieved
     * @param deepScan  whether to scan superclasses and interfaces for methods
     * @return a collection of methods from the class and optionally from its superclasses and interfaces
     */
    public static Collection<Method> getAllMethods(Class<?> clazz, boolean deepScan) {
        Set<Method> methods = new HashSet<>(Set.of(clazz.getDeclaredMethods()));

        // Optionally scan superclasses for methods (except Object.class)
        if (deepScan && clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            methods.addAll(getAllMethods(clazz.getSuperclass(), true));
        }

        // Optionally scan interfaces for methods
        if (deepScan) {
            for (Class<?> ifc : clazz.getInterfaces()) {
                methods.addAll(getAllMethods(ifc, true));
            }
        }

        return methods;
    }

}
