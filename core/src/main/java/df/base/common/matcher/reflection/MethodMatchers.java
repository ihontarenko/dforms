package df.base.common.matcher.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.TextMatchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

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

    public static Matcher<Method> throwsException(Class<? extends Throwable> exceptionType) {
        return (method, context) -> List.of(method.getExceptionTypes()).contains(exceptionType);
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

    private record MethodNameWithMatcher(Matcher<? super String> textMatcher) implements Matcher<Method> {
        @Override
        public boolean matches(Method item, MatchContext context) {
            return textMatcher.matches(item.getName(), context);
        }
    }

    private record ParameterTypesMatcher(Class<?>... expectedTypes) implements Matcher<Method> {
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

    private record ParameterCountMatcher(int expectedCount) implements Matcher<Method> {
        @Override
        public boolean matches(Method method, MatchContext context) {
            return method.getParameterCount() == expectedCount;
        }
    }

    private record ModifierMatcher(int modifier) implements Matcher<Method> {
        @Override
        public boolean matches(Method method, MatchContext context) {
            return (method.getModifiers() & modifier) != 0;
        }
    }

    private record AnnotatedMatcher(Class<? extends Annotation> annotation) implements Matcher<Method> {
        @Override
        public boolean matches(Method method, MatchContext context) {
            return method.isAnnotationPresent(annotation);
        }
    }

    private record ReturnTypeMatcher(Class<?> returnType) implements Matcher<Method> {
        @Override
        public boolean matches(Method method, MatchContext context) {
            return method.getReturnType().equals(returnType);
        }
    }

}