package df.base.common.matcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@SuppressWarnings({"unused"})
public class MethodMatchers {

    public static Matcher<Method> withModifier(int modifier) {
        return new ModifierMatcher(modifier);
    }

    public static Matcher<Method> isPrivate() {
        return withModifier(Modifier.PRIVATE);
    }

    public static Matcher<Method> isProtected() {
        return withModifier(Modifier.PROTECTED);
    }

    public static Matcher<Method> isPublic() {
        return withModifier(Modifier.PUBLIC);
    }

    public static Matcher<Method> isAnnotatedWith(Class<? extends Annotation> annotation) {
        return new AnnotatedMatcher(annotation);
    }

    public static Matcher<Method> hasReturnType(Class<?> returnType) {
        return new ReturnTypeMatcher(returnType);
    }

    public static Matcher<Method> hasParameterCount(int count) {
        return new ParameterCountMatcher(count);
    }

    public static Matcher<Method> hasParameterTypes(Class<?>... parameterTypes) {
        return new ParameterTypesMatcher(parameterTypes);
    }

    public static Matcher<Method> nameStarts(String prefix) {
        return withName(TextMatchers.startsWith(prefix));
    }

    public static Matcher<Method> nameEnds(String suffix) {
        return withName(TextMatchers.endsWith(suffix));
    }

    public static Matcher<Method> nameContains(String substring) {
        return withName(TextMatchers.contains(substring));
    }

    public static Matcher<Method> nameEquals(String actual) {
        return withName(TextMatchers.same(actual));
    }

    public static Matcher<Method> withName(Matcher<? super String> textMatcher) {
        return new MethodNameWithMatcher(textMatcher);
    }

    static class MethodNameWithMatcher implements Matcher<Method> {

        private final Matcher<? super String> textMatcher;

        MethodNameWithMatcher(Matcher<? super String> textMatcher) {
            this.textMatcher = textMatcher;
        }

        @Override
        public boolean matches(Method item, MatchContext context) {
            return textMatcher.matches(item.getName(), context);
        }

    }

    static class ParameterTypesMatcher implements Matcher<Method> {
        private final Class<?>[] expectedTypes;

        public ParameterTypesMatcher(Class<?>... expectedTypes) {
            this.expectedTypes = expectedTypes;
        }

        @Override
        public boolean matches(Method method, MatchContext context) {
            Class<?>[] actualTypes = method.getParameterTypes();

            if (actualTypes.length != expectedTypes.length) {
                return false;
            }

            for (int i = 0; i < actualTypes.length; i++) {
                if (!actualTypes[i].equals(expectedTypes[i])) {
                    return false;
                }
            }

            return true;
        }

    }

    static class ParameterCountMatcher implements Matcher<Method> {

        private final int expectedCount;

        public ParameterCountMatcher(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public boolean matches(Method method, MatchContext context) {
            return method.getParameterCount() == expectedCount;
        }

    }

    static class ModifierMatcher implements Matcher<Method> {

        private final int modifier;

        public ModifierMatcher(int modifier) {
            this.modifier = modifier;
        }

        @Override
        public boolean matches(Method method, MatchContext context) {
            return (method.getModifiers() & modifier) != 0;
        }

    }

    static class AnnotatedMatcher implements Matcher<Method> {

        private final Class<? extends Annotation> annotation;

        public AnnotatedMatcher(Class<? extends Annotation> annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean matches(Method method, MatchContext context) {
            return method.isAnnotationPresent(annotation);
        }

    }

    static class ReturnTypeMatcher implements Matcher<Method> {

        private final Class<?> returnType;

        public ReturnTypeMatcher(Class<?> returnType) {
            this.returnType = returnType;
        }

        @Override
        public boolean matches(Method method, MatchContext context) {
            return method.getReturnType().equals(returnType);
        }

    }

}