package df.common.reflection;

import df.common.matcher.Matcher;
import df.common.matcher.reflection.MemberMatcher;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.Comparator;

/**
 * An abstract class that implements the base logic for finding class members (fields, methods, constructors)
 * based on specific matching conditions defined by {@link Matcher}, with support for sorting.
 *
 * <p>This class provides the following functionality:</p>
 * <ul>
 *     <li>Retrieves members (fields, methods, or constructors) of a class using {@link #getMembers(Class)}
 *         or {@link #getMembers(Class, boolean)} methods, which should be implemented by subclasses.</li>
 *     <li>Finds members that match a specific {@link Matcher}.</li>
 *     <li>Applies a collection of {@link Comparator}s to sort the matching members.</li>
 *     <li>Supports strict matching against the declaring class or relaxed matching based on the provided matcher.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Using the MethodFinder to retrieve and sort methods
 * MethodFinder finder = new MethodFinder();
 * Collection<Method> methods = finder.find(
 *     MyClass.class,
 *     MemberMatcher.isPublic(), // Match only public methods
 *     List.of(
 *         Comparator.comparing(Member::getName),                  // Sort by name
 *         Comparator.comparingInt(Method::getParameterCount)      // Sort by parameter count
 *     )
 * );
 * methods.forEach(method -> System.out.println(method.getName()));
 * }</pre>
 *
 * @param <T> the type of member being searched (e.g., {@link java.lang.reflect.Field}, {@link java.lang.reflect.Method}, {@link java.lang.reflect.Constructor})
 */
public abstract class AbstractFinder<T extends Member> implements MemberFinder<T> {

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
     * Returns a collection of members from the specified class.
     * This method should be implemented by subclasses to define which members are to be searched
     * (e.g., methods, fields, constructors).
     *
     * @param clazz the class whose members should be retrieved
     * @param deepScan whether to scan superclasses for members
     * @return a collection of members from the class
     */
    protected abstract Collection<T> getMembers(Class<?> clazz, boolean deepScan);

    /**
     * Finds all members of the specified class that match the given {@link Matcher}.
     * The results are sorted using the provided collection of {@link Comparator}s.
     *
     * <p>This method applies a strict matching mode first, ensuring the member belongs
     * to the declaring class. If no matches are found, it relaxes the condition
     * and applies the matcher to all members of the class.</p>
     *
     * @param clazz       the class whose members are to be searched
     * @param matcher     the matcher to filter the members
     * @param comparators a collection of comparators to sort the matched members; if empty, no sorting is applied
     * @return a collection of members that match the criteria, sorted as specified
     */
    @Override
    public Collection<T> find(Class<?> clazz, Matcher<? super T> matcher, Collection<Comparator<T>> comparators) {
        Matcher<Member> strictMatcher = MemberMatcher.isDeclaredClass(clazz).and((Matcher<? super Member>) matcher);
        Collection<T>   members       = getMembers(clazz, true);

        Collection<T> matched = members.stream()
                .filter(strictMatcher::matches).toList();

        if (matched.isEmpty()) {
            matched = members.stream().filter(matcher::matches).toList();
        }

        Comparator<T> comparator = comparators.stream()
                .reduce(Comparator::thenComparing)
                .orElse((a, b) -> 0);

        return matched.stream().sorted(comparator).toList();
    }
}
