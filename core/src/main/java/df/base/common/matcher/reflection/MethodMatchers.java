package df.base.common.matcher.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.TextMatchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static df.base.common.matcher.reflection.TypeMatchers.isSimilar;
import static df.base.common.matcher.reflection.TypeMatchers.isSubtype;

@SuppressWarnings({"unused"})
public class MethodMatchers {

    public static Matcher<Executable> withModifier(int modifier) {
        return new ModifierMatcher(modifier);
    }

    public static Matcher<Executable> isPrivate() {
        return withModifier(Modifier.PRIVATE);
    }

    public static Matcher<Executable> isProtected() {
        return withModifier(Modifier.PROTECTED);
    }

    public static Matcher<Executable> isPublic() {
        return withModifier(Modifier.PUBLIC);
    }

    public static Matcher<Executable> isStatic() {
        return withModifier(Modifier.STATIC);
    }

    public static Matcher<Executable> isFinal() {
        return withModifier(Modifier.FINAL);
    }

    public static Matcher<Method> isDefault() {
        return new IsDefaultMethodMatcher();
    }

    public static Matcher<Executable> isAnnotatedWith(Class<? extends Annotation> annotation) {
        return new AnnotatedMatcher(annotation);
    }

    public static Matcher<Method> hasReturnType(Class<?> returnType) {
        return new ReturnTypeMatcher(returnType);
    }

    public static Matcher<Executable> hasParameterCount(int count) {
        return new ParameterCountMatcher(count);
    }

    public static Matcher<Executable> hasParameterTypes(Class<?>... parameterTypes) {
        return new ParameterTypesMatcher(parameterTypes);
    }

    public static Matcher<Executable> hasSoftParameterTypes(Class<?>... parameterTypes) {
        return new SoftParameterTypesMatcher(parameterTypes);
    }

    public static Matcher<Executable> throwsException(Class<? extends Throwable> exceptionType) {
        return (method, context) -> List.of(method.getExceptionTypes()).contains(exceptionType);
    }

    public static Matcher<Executable> nameStarts(String prefix) {
        return withName(TextMatchers.startsWith(prefix));
    }

    public static Matcher<Executable> nameEnds(String suffix) {
        return withName(TextMatchers.endsWith(suffix));
    }

    public static Matcher<Executable> nameContains(String substring) {
        return withName(TextMatchers.contains(substring));
    }

    public static Matcher<Executable> nameEquals(String actual) {
        return withName(TextMatchers.same(actual));
    }

    public static Matcher<Executable> withName(Matcher<? super String> textMatcher) {
        return new MethodNameWithMatcher(textMatcher);
    }

    public static Matcher<Constructor<?>> isDefaultConstructor() {
        return new DefaultConstructorMatcher();
    }

    public static Matcher<Constructor<?>> isCopyConstructor() {
        return new CopyConstructorMatcher();
    }

    private record CopyConstructorMatcher() implements Matcher<Constructor<?>> {
        @Override
        public boolean matches(Constructor<?> constructor, MatchContext context) {
            return Matcher.and(hasParameterCount(1), hasParameterTypes(constructor.getDeclaringClass()))
                    .matches(constructor, context);
        }
    }

    private record DefaultConstructorMatcher() implements Matcher<Constructor<?>> {
        @Override
        public boolean matches(Constructor<?> item, MatchContext context) {
            return hasParameterCount(0).matches(item, context);
        }
    }

    private record MethodNameWithMatcher(Matcher<? super String> textMatcher) implements Matcher<Executable> {
        @Override
        public boolean matches(Executable item, MatchContext context) {
            return textMatcher.matches(item.getName(), context);
        }
    }

    private record ParameterTypesMatcher(Class<?>... expectedTypes) implements Matcher<Executable> {
        @Override
        public boolean matches(Executable method, MatchContext context) {
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

    private record ParameterCountMatcher(int expectedCount) implements Matcher<Executable> {
        @Override
        public boolean matches(Executable method, MatchContext context) {
            return method.getParameterCount() == expectedCount;
        }
    }

    private record SoftParameterTypesMatcher(Class<?>... expectedTypes) implements Matcher<Executable> {
        @Override
            public boolean matches(Executable method, MatchContext context) {
                Class<?>[] actualTypes = method.getParameterTypes();

                if (actualTypes.length != expectedTypes.length) {
                    return false;
                }

                for (int i = 0; i < actualTypes.length; i++) {
                    Matcher<Class<?>> matcher = isSubtype(expectedTypes[i]).or(isSimilar(expectedTypes[i]));
                    if (!matcher.matches(actualTypes[i], context)) {
                        return false;
                    }
                }

                return true;
            }
        }

    private record IsDefaultMethodMatcher() implements Matcher<Method> {
        @Override
        public boolean matches(Method method, MatchContext context) {
            return method.isDefault();
        }
    }

    private record ModifierMatcher(int modifier) implements Matcher<Executable> {
        @Override
        public boolean matches(Executable method, MatchContext context) {
            return (method.getModifiers() & modifier) != 0;
        }
    }

    private record AnnotatedMatcher(Class<? extends Annotation> annotation) implements Matcher<Executable> {
        @Override
        public boolean matches(Executable method, MatchContext context) {
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