package df.base.common.matcher.reflection;

import df.base.common.matcher.Matcher;

import static df.base.common.reflection.JavaTypes.PRIMITIVES;
import static df.base.common.reflection.JavaTypes.WRAPPERS;

/**
 * A utility class that provides matchers for comparing types. These matchers allow for checking
 * if two types are similar (i.e., primitive and its wrapper) or if one type is a subtype of another.
 */
public class TypeMatchers {

    /**
     * Returns a matcher that checks if the given type is similar to the expected type.
     * This matcher considers a primitive type and its corresponding wrapper type as similar.
     *
     * @param expectedType the expected type
     * @return a matcher that returns true if the given type is similar to the expected type
     * @example
     * <pre>{@code
     * Matcher<Class<?>> matcher = TypeMatchers.isSimilar(int.class);
     * boolean result = matcher.matches(Integer.class); // returns true
     * }</pre>
     * @see MethodMatchers#hasSoftParameterTypes(Class[])
     */
    public static Matcher<Class<?>> isSimilar(Class<?> expectedType) {
        return new SimilarTypeMatcher(expectedType);
    }

    /**
     * Returns a matcher that checks if the given type is the same to the expected type.
     *
     * @param expectedType the expected type
     * @return a matcher that returns true if the given type is the same to the expected type
     */
    public static Matcher<Class<?>> isSame(Class<?> expectedType) {
        return expectedType::equals;
    }

    /**
     * Returns a matcher that checks if the given type is a subtype of the expected type.
     * This matcher uses {@linkplain Class#isAssignableFrom} to determine if one type is a subtype of another.
     *
     * @param expectedType the expected type
     * @return a matcher that returns true if the given type is a subtype of the expected type
     * @example
     * <pre>{@code
     * Matcher<Class<?>> matcher = TypeMatchers.isSubtype(Number.class);
     * boolean result = matcher.matches(Integer.class); // returns true
     * }</pre>
     */
    public static Matcher<Class<?>> isSubtype(Class<?> expectedType) {
        return new SubtypeTypeMatcher(expectedType);
    }


    /**
     * Returns a matcher that checks if the given type is a supertype of the expected type.
     * This matcher uses {@linkplain Class#isAssignableFrom} to determine if one type is a supertype of another.
     *
     * @param expectedType the expected type
     * @return a matcher that returns true if the given type is a supertype of the expected type
     * @example
     * <pre>{@code
     * Matcher<Class<?>> matcher = TypeMatchers.isSupertype(Integer.class);
     * boolean result = matcher.matches(Number.class); // returns true
     * }</pre>
     */
    public static Matcher<Class<?>> isSupertype(Class<?> expectedType) {
        return new SupertypeTypeMatcher(expectedType);
    }

    /**
     * A matcher that checks if two types are similar, meaning a primitive type and its wrapper class
     * are considered equivalent.
     */
    private record SimilarTypeMatcher(Class<?> expectedType) implements Matcher<Class<?>> {
        @Override
        public boolean matches(Class<?> actualType) {
            // If the types are not exactly the same, check for primitive/wrapper equivalence
            if (!expectedType.equals(actualType)) {
                Class<?> wrapper = expectedType.isPrimitive() ? WRAPPERS.get(expectedType) : PRIMITIVES.get(expectedType);
                return wrapper != null && wrapper.equals(actualType);
            }
            return true;
        }
    }

    /**
     * A matcher that checks if the actual type is a subtype of the expected type.
     * This uses {@linkplain Class#isAssignableFrom} to determine if the types are related.
     */
    private record SubtypeTypeMatcher(Class<?> expectedType) implements Matcher<Class<?>> {
        @Override
        public boolean matches(Class<?> actualType) {
            return actualType.isAssignableFrom(expectedType);
        }
    }

    /**
     * A matcher that checks if the actual type is a super-type of the expected type.
     * This uses {@linkplain Class#isAssignableFrom} to determine if the types are related.
     */
    private record SupertypeTypeMatcher(Class<?> expectedType) implements Matcher<Class<?>> {
        @Override
        public boolean matches(Class<?> actualType) {
            return expectedType.isAssignableFrom(actualType);
        }
    }
}
