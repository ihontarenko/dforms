package df.common.reflection;

import df.common.matcher.Matcher;

import java.lang.reflect.Member;
import java.util.List;

/**
 * An abstract base class for implementing filters that work with reflection members.
 *
 * The {@code AbstractFilter} class provides a common implementation for filtering reflection members (such as methods,
 * fields, or constructors) based on a {@link Matcher}. This class supports finding matching members from a specified
 * class type using the provided matcher.
 *
 * <p>Example usage:
 * <pre>{@code
 *     AbstractFilter<Method> methodFilter = new MethodFilter(finder, matcher, SomeClass.class);
 *     List<Method> methods = methodFilter.find();
 * }</pre>
 *
 * @param <T> the type of {@link Member} (e.g., {@link java.lang.reflect.Method}, {@link java.lang.reflect.Field})
 */
abstract public class AbstractFilter<T extends Member> implements Filter<T> {

    /** The finder used to locate members. */
    protected final MemberFinder<T> finder;

    /** The matcher used to apply filtering criteria. */
    protected Matcher<T> matcher;

    /** The class type to filter members from. */
    protected final Class<?> type;

    /**
     * Constructs a new {@code AbstractFilter} with the given parameters.
     *
     * @param finder the {@link MemberFinder} to be used for finding members
     * @param matcher the {@link Matcher} to be applied to the members
     * @param type the class type to filter members from
     */
    public AbstractFilter(MemberFinder<T> finder, Matcher<T> matcher, Class<?> type) {
        this.finder = finder;
        this.matcher = matcher;
        this.type = type;
    }

    /**
     * Finds all members of the specified type that match the given matcher.
     *
     * This method uses the provided {@link MemberFinder} to search for members in the specified class type and filters
     * them using the current {@link Matcher}.
     *
     * @return a list of matching members
     */
    @Override
    public List<T> find() {
        return finder.find(type, matcher);
    }

    /**
     * Gets the current matcher being used to filter the members.
     *
     * @return the {@link Matcher} used to filter the members
     */
    @Override
    public Matcher<T> matcher() {
        return matcher;
    }
}