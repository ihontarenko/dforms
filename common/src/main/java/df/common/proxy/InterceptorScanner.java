package df.common.proxy;

import df.common.container.scanner.ClassScanner;
import df.common.proxy.annotation.Interceptor;
import df.common.matcher.reflection.ClassMatchers;
import df.common.matcher.reflection.TypeMatchers;

import java.util.*;

public class InterceptorScanner {

    private static final ClassScanner                SCANNER = ClassScanner.getDefaultScanner();
    private static final Map<Integer, Set<Class<?>>> CACHE   = new HashMap<>();

    public static Set<Class<?>> getMethodInterceptorClasses(Class<?>... baseClasses) {
        int cacheKey = Objects.hash((Object) baseClasses);

        if (!CACHE.containsKey(cacheKey)) {
            Set<Class<?>> classes = new HashSet<>();

            SCANNER.setMatcher(
                    ClassMatchers.isAnnotatedWith(Interceptor.class).and(TypeMatchers.isSupertype(MethodInterceptor.class)));

            for (Class<?> baseClass : baseClasses) {
                classes.addAll(SCANNER.scan(baseClass.getPackageName(), baseClass.getClassLoader()));
            }

            CACHE.put(cacheKey, classes);
        }

        return CACHE.get(cacheKey);
    }

}
