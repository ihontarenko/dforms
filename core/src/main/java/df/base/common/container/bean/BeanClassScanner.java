package df.base.common.container.bean;

import df.base.common.container.scanner.ClassScanner;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.ClassMatchers;

import java.util.HashSet;
import java.util.Set;

import static df.base.common.matcher.reflection.MethodMatchers.isAnnotatedWith;
import static df.base.common.matcher.reflection.MethodMatchers.isPublic;

public class BeanClassScanner extends ClassScanner {

    public static final  Matcher<Class<?>> METHOD_BEAN_ANNOTATED_CLASS_FILTER         = ClassMatchers.hasMethod(
            isAnnotatedWith(Bean.class).and(isPublic()));
    public static final  Matcher<Class<?>> CLASS_BEAN_ANNOTATED_CLASS_FILTER          = ClassMatchers.isAnnotatedWith(
            Bean.class);
    public static final  Matcher<Class<?>> CLASS_CONFIGURATION_ANNOTATED_CLASS_FILTER = ClassMatchers.isAnnotatedWith(
            BeansProvider.class);
    private static final ClassScanner      CLASS_SCANNER                              = ClassScanner.getDefaultScanner();

    public static Set<Class<?>> scanPackagesWithFilter(Matcher<Class<?>> filter, Class<?>... baseClasses) {
        Set<Class<?>> classes = new HashSet<>();
        ClassLoader   loader  = Thread.currentThread().getContextClassLoader();

        // matching
        CLASS_SCANNER.setMatcher(filter);

        for (Class<?> baseClass : baseClasses) {
            classes.addAll(CLASS_SCANNER.scan(baseClass.getPackageName(), loader));
        }

        return classes;
    }

}
