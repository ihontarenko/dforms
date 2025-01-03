package df.common.reflection;

import df.common.matcher.Matcher;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

/**
 * Interface for finding and filtering reflection members in a class.
 *
 * The {@code Finder} interface provides methods for searching reflection members (e.g., methods, fields, constructors)
 * based on a specified {@link Matcher}. It supports finding all matching members or just the first match.
 * Additionally, it allows filtering members based on certain criteria.
 *
 * @param <T> the type of {@link Member} (e.g., {@link java.lang.reflect.Method}, {@link java.lang.reflect.Field})
 */
public interface MemberFinder<T extends Member> {

    /**
     * Finds all members of the specified class that match the given {@link Matcher}.
     *
     * @param clazz the class to search for members in
     * @param matcher the matcher used to filter the members
     * @return a list of members matching the criteria
     */
    List<T> find(Class<?> clazz, Matcher<? super T> matcher);

    /**
     * Finds the first member of the specified class that matches the given {@link Matcher}.
     * This method calls {@link #find(Class, Matcher)} and returns the first element in the list,
     * if any match is found.
     *
     * @param clazz the class to search for members in
     * @param matcher the matcher used to filter the members
     * @return an {@link Optional} containing the first matching member, or empty if no match is found
     */
    default Optional<T> findFirst(Class<?> clazz, Matcher<? super T> matcher) {
        return find(clazz, matcher).stream().findFirst();
    }

    /**
     * Returns a {@link Filter} to apply additional filtering criteria to members of the specified class.
     *
     * @param clazz the class to filter members from
     * @return a {@link Filter} instance for filtering members
     */
    Filter<T> filter(Class<?> clazz);
}
