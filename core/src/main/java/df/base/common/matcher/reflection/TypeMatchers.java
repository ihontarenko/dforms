package df.base.common.matcher.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;

import static df.base.common.reflection.JavaTypes.PRIMITIVES;
import static df.base.common.reflection.JavaTypes.WRAPPERS;

public class TypeMatchers {

    public static Matcher<Class<?>> isSimilar(Class<?> expectedType) {
        return new SimilarTypeMatcher(expectedType);
    }

    public static Matcher<Class<?>> isSubtype(Class<?> expectedType) {
        return new SubtypeTypeMatcher(expectedType);
    }

    private record SimilarTypeMatcher(Class<?> expectedType) implements Matcher<Class<?>> {
        @Override
        public boolean matches(Class<?> actualType, MatchContext context) {
            if (!expectedType.equals(actualType)) {
                Class<?> wrapper = expectedType.isPrimitive() ? WRAPPERS.get(expectedType) : PRIMITIVES.get(expectedType);
                return wrapper != null && wrapper.equals(actualType);
            }
            return true;
        }
    }

    private record SubtypeTypeMatcher(Class<?> expectedType) implements Matcher<Class<?>> {
        @Override
        public boolean matches(Class<?> actualType, MatchContext context) {
            return actualType.isAssignableFrom(expectedType);
        }
    }

}
