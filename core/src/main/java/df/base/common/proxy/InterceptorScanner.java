package df.base.common.proxy;

import df.base.common.libs.jbm.scanner.ClassScanner;
import df.base.common.proxy.annotation.Interceptor;

import java.util.*;

import static df.base.common.matcher.reflection.ClassMatchers.isAnnotatedWith;
import static df.base.common.matcher.reflection.TypeMatchers.isSupertype;

public class InterceptorScanner {

    private static final ClassScanner                SCANNER = ClassScanner.getDefaultScanner();
    private static final Map<Integer, Set<Class<?>>> CACHE   = new HashMap<>();

    public static Set<Class<?>> getMethodInterceptorClasses(Class<?>... baseClasses) {
        int cacheKey = Objects.hash((Object) baseClasses);

        if (!CACHE.containsKey(cacheKey)) {
            Set<Class<?>> classes = new HashSet<>();

            SCANNER.setMatcher(isAnnotatedWith(Interceptor.class).and(isSupertype(MethodInterceptor.class)));

            for (Class<?> baseClass : baseClasses) {
                classes.addAll(SCANNER.scan(baseClass.getPackageName(), baseClass.getClassLoader()));
            }

            CACHE.put(cacheKey, classes);
        }

        return CACHE.get(cacheKey);
    }

}
