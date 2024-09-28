package df.base.common.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

/**
 * Interface for finding class members (fields, methods, constructors)
 * that match specific conditions, represented by {@link Matcher}.
 *
 * @param <T> the type of member being searched (e.g., Field, Method, Constructor)
 */
public interface Finder<T extends Member> {

    /**
     * Finds all members of the given class that match the specified {@link Matcher}.
     *
     * @param clazz   the class whose members are to be searched
     * @param matcher the matcher to apply for filtering members
     * @param context an optional context that can be used to pass additional information during matching
     * @return a list of members that match the given conditions
     * @example
     * <pre>{@code
     * Finder<Method> finder = new MethodFinder();
     * Matcher<Method> matcher = MethodMatchers.isPublic();
     * // iteration over all public methods
     * finder.find(SomeClass.class, matcher, context)
     *      .forEach(method -> System.out.println(method.getName()));
     * }</pre>
     */
    List<T> find(Class<?> clazz, Matcher<? super T> matcher, MatchContext context);

    /**
     * Finds the first member of the given class that matches the specified {@link Matcher}.
     * This method returns an {@link Optional}, which will be empty if no match is found.
     *
     * @param clazz   the class whose members are to be searched
     * @param matcher the matcher to apply for filtering members
     * @param context an optional context that can be used to pass additional information during matching
     * @return an {@link Optional} containing the first matching member, or {@link Optional#empty()} if no match is found
     * @example
     * <pre>{@code
     * // Example: Finding the first public method in a class
     * Finder<Method> methodFinder = new MethodFinder();
     * Optional<Method> firstPublicMethod = methodFinder.findFirst(SomeClass.class, MethodMatchers.isPublic(), context);
     * firstPublicMethod.ifPresent(method -> System.out.println("Found method: " + method.getName()));
     * }</pre>
     */
    default Optional<T> findFirst(Class<?> clazz, Matcher<? super T> matcher, MatchContext context) {
        return find(clazz, matcher, context).stream().findFirst();
    }

}