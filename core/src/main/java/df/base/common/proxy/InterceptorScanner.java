package df.base.common.proxy;

import df.base.common.libs.jbm.scanner.ClassScanner;
import df.base.common.libs.jbm.scanner.filter.type.ClassAnnotatedClassFilter;
import df.base.common.libs.jbm.scanner.filter.type.SubclassClassFilter;
import df.base.common.libs.jbm.scanner.filter.type.TypeFilter;
import df.base.common.proxy.annotation.Interceptor;

import java.util.*;

public class InterceptorScanner {

    private static final TypeFilter                  CLASS_ANNOTATED_INTERCEPTOR = new ClassAnnotatedClassFilter(
            Interceptor.class);
    private static final TypeFilter                  INTERCEPTOR_CLASSES         = new SubclassClassFilter(
            MethodInterceptor.class);
    private static final ClassScanner                SCANNER                     = ClassScanner.getDefaultScanner();
    private static final Map<Integer, Set<Class<?>>> CACHE                       = new HashMap<>();

    public static Set<Class<?>> getMethodInterceptorClasses(Class<?>... baseClasses) {
        int cacheKey = Objects.hash((Object) baseClasses);

        if (!CACHE.containsKey(cacheKey)) {
            Set<Class<?>> classes     = new HashSet<>();
            ClassLoader   classLoader = Thread.currentThread().getContextClassLoader();

            SCANNER.clearFilters();
            SCANNER.addFilter(INTERCEPTOR_CLASSES);
            SCANNER.addFilter(CLASS_ANNOTATED_INTERCEPTOR);

            for (Class<?> baseClass : baseClasses) {
                classes.addAll(SCANNER.scan(baseClass.getPackageName(), classLoader));
            }

            CACHE.put(cacheKey, classes);
        }

        return CACHE.get(cacheKey);
    }

}
