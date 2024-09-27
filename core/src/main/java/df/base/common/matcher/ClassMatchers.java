package df.base.common.matcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassMatchers {

    public static Matcher<Class<?>> withModifier(int modifier) {
        return new ModifierMatcher(modifier);
    }

    public static Matcher<Class<?>> isPrivate() {
        return withModifier(Modifier.PRIVATE);
    }

    public static Matcher<Class<?>> isProtected() {
        return withModifier(Modifier.PROTECTED);
    }

    public static Matcher<Class<?>> isPublic() {
        return withModifier(Modifier.PUBLIC);
    }

    public static Matcher<Class<?>> isAbstract() {
        return withModifier(Modifier.ABSTRACT);
    }

    public static Matcher<Class<?>> implementsInterface(Class<?> interfaceClass) {
        return new ImplementsInterfaceMatcher(interfaceClass);
    }

    public static Matcher<Class<?>> isAnnotatedWith(Class<? extends Annotation> annotation) {
        return new AnnotatedClassMatcher(annotation);
    }

    public static Matcher<Class<?>> hasField(Matcher<? super Field> fieldMatcher) {
        return new HasFieldMatcher(fieldMatcher);
    }

    public static Matcher<Class<?>> hasMethod(Matcher<? super Method> methodMatcher) {
        return new HasMethodMatcher(methodMatcher);
    }

    public static Matcher<Class<?>> hasFieldName(Matcher<? super String> textMatcher) {
        return hasField(FieldMatchers.withName(textMatcher));
    }

    public static Matcher<Class<?>> hasMethodName(Matcher<? super String> textMatcher) {
        return hasMethod(MethodMatchers.withName(textMatcher));
    }

    public static Matcher<Class<?>> hasMethodAnnotatedWith(Class<? extends Annotation> annotation) {
        return new HasMethodMatcher(MethodMatchers.isAnnotatedWith(annotation));
    }

    public static Matcher<Class<?>> hasFieldAnnotatedWith(Class<? extends Annotation> annotation) {
        return new HasFieldMatcher(FieldMatchers.isAnnotatedWith(annotation));
    }

    public static Matcher<Class<?>> nameStarts(String prefix) {
        return withName(TextMatchers.startsWith(prefix));
    }

    public static Matcher<Class<?>> nameEnds(String suffix) {
        return withName(TextMatchers.endsWith(suffix));
    }

    public static Matcher<Class<?>> nameContains(String substring) {
        return withName(TextMatchers.contains(substring));
    }

    public static Matcher<Class<?>> nameEquals(String actual) {
        return withName(TextMatchers.same(actual));
    }

    public static Matcher<Class<?>> withName(Matcher<? super String> textMatcher) {
        return new ClassNameWithMatcher(textMatcher);
    }

    static class ClassNameWithMatcher implements Matcher<Class<?>> {

        private final Matcher<? super String> textMatcher;

        ClassNameWithMatcher(Matcher<? super String> textMatcher) {
            this.textMatcher = textMatcher;
        }

        @Override
        public boolean matches(Class<?> item, MatchContext context) {
            return textMatcher.matches(item.getName(), context);
        }

    }

    static class ImplementsInterfaceMatcher implements Matcher<Class<?>> {
        private final Class<?> interfaceClass;

        public ImplementsInterfaceMatcher(Class<?> interfaceClass) {
            this.interfaceClass = interfaceClass;
        }

        @Override
        public boolean matches(Class<?> clazz, MatchContext context) {
            for (Class<?> implementedInterface : clazz.getInterfaces()) {
                if (implementedInterface.equals(interfaceClass)) {
                    return true;
                }
            }
            return false;
        }
    }

    static class ModifierMatcher implements Matcher<Class<?>> {

        private final int modifier;

        public ModifierMatcher(int modifier) {
            this.modifier = modifier;
        }

        @Override
        public boolean matches(Class<?> clazz, MatchContext context) {
            return (clazz.getModifiers() & modifier) != 0;
        }

    }

    static class AnnotatedClassMatcher implements Matcher<Class<?>> {

        private final Class<? extends Annotation> annotation;

        public AnnotatedClassMatcher(Class<? extends Annotation> annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean matches(Class<?> clazz, MatchContext context) {
            return clazz.isAnnotationPresent(annotation);
        }

    }

    static class MethodAnnotatedMatcher implements Matcher<Class<?>> {

        private final Class<? extends Annotation> annotation;

        public MethodAnnotatedMatcher(Class<? extends Annotation> annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean matches(Class<?> clazz, MatchContext context) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    return true;
                }
            }
            return false;
        }

    }

    static class HasFieldMatcher implements Matcher<Class<?>> {

        private final Matcher<? super Field> fieldMatcher;

        public HasFieldMatcher(Matcher<? super Field> fieldMatcher) {
            this.fieldMatcher = fieldMatcher;
        }

        @Override
        public boolean matches(Class<?> clazz, MatchContext context) {
            for (Field field : clazz.getDeclaredFields()) {
                if (fieldMatcher.matches(field, context)) {
                    return true;
                }
            }
            return false;
        }

    }

    static class HasFieldNameMatcher implements Matcher<Class<?>> {

        private final Matcher<? super String> fieldMatcher;

        public HasFieldNameMatcher(Matcher<? super String> fieldMatcher) {
            this.fieldMatcher = fieldMatcher;
        }

        @Override
        public boolean matches(Class<?> clazz, MatchContext context) {
            for (Field field : clazz.getDeclaredFields()) {
                if (fieldMatcher.matches(field.getName(), context)) {
                    return true;
                }
            }
            return false;
        }

    }

    static class HasMethodMatcher implements Matcher<Class<?>> {

        private final Matcher<? super Method> methodMatcher;

        public HasMethodMatcher(Matcher<? super Method> methodMatcher) {
            this.methodMatcher = methodMatcher;
        }

        @Override
        public boolean matches(Class<?> clazz, MatchContext context) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (methodMatcher.matches(method, context)) {
                    return true;
                }
            }
            return false;
        }

    }

}
