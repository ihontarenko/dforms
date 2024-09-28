package df.base.common.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An abstract class that implements the base logic for finding class members (fields, methods, constructors)
 * based on specific matching conditions defined by {@link Matcher}.
 *
 * @param <T> the type of member being searched (e.g., Field, Method, Constructor)
 */
public abstract class AbstractFinder<T extends Member> implements Finder<T> {

    /**
     * Returns a collection of members from the specified class.
     * This method should be implemented by subclasses to define which members are to be searched
     * (e.g., methods, fields, constructors).
     *
     * @param clazz the class whose members should be retrieved
     * @return a collection of members from the class
     */
    protected abstract Collection<T> getMembers(Class<?> clazz);

    /**
     * Finds all members of the given class that match the specified {@link Matcher}.
     * This method iterates over all the members retrieved by {@link #getMembers(Class)} and applies the matcher.
     *
     * @param clazz   the class whose members are to be searched
     * @param matcher the matcher to apply for filtering members
     * @param context an optional context that can be used to pass additional information during matching
     * @return a list of members that match the given conditions
     * @example
     * <pre>{@code
     * // Example: Finding all public methods in a class
     * Finder<Method> methodFinder = new MethodFinder();
     * List<Method> publicMethods = methodFinder.find(SomeClass.class, MethodMatchers.isPublic(), context);
     * publicMethods.forEach(method -> System.out.println(method.getName()));
     * }</pre>
     */
    @Override
    public List<T> find(Class<?> clazz, Matcher<? super T> matcher, MatchContext context) {
        List<T> matchedMembers = new ArrayList<>();

        // Iterates through all members of the class and applies the matcher
        for (T member : getMembers(clazz)) {
            if (matcher.matches(member, context)) {
                matchedMembers.add(member);
            }
        }

        return matchedMembers;
    }

}