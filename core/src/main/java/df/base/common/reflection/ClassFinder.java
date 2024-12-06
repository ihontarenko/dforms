package df.base.common.reflection;

import df.base.common.libs.jbm.scanner.ClassScanner;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.ClassMatchers;

import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public interface ClassFinder {

    Map<Integer, Set<Class<?>>> CACHE   = new HashMap<>();
    ClassScanner                SCANNER = ClassScanner.getDefaultScanner();

    static Set<Class<?>> findAnnotatedClasses(Class<? extends Annotation> annotation, Class<?>... baseClasses) {
        return findAll(ClassMatchers.isAnnotatedWith(annotation), baseClasses);
    }

    static Set<Class<?>> findEnums(Class<?>... baseClasses) {
        return findAll(ClassMatchers.isEnum(), baseClasses);
    }

    static Set<Class<?>> findImplementations(Class<?> interfaceClass, Class<?>... baseClasses) {
        return findAll(ClassMatchers.implementsInterface(interfaceClass).and(ClassMatchers.isAbstract().not()),
                       baseClasses);
    }

    static Set<Class<?>> findAll(Matcher<Class<?>> matcher, Class<?>... baseClasses) {
        return findAll(baseClasses).stream().filter(matcher::matches).collect(toSet());
    }

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
