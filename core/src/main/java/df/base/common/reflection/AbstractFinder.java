package df.base.common.reflection;

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
     * Finds all members of the given class that match the specified {@link Matcher}.
     * This method iterates over all the members retrieved by {@link #getMembers(Class)} and applies the matcher.
     *
     * @param clazz   the class whose members are to be searched
     * @param matcher the matcher to apply for filtering members
     * @return a list of members that match the given conditions
     */
    @Override
    public List<T> find(Class<?> clazz, Matcher<? super T> matcher) {
        List<T> matchedMembers = new ArrayList<>();

        // todo: optimize duplicates of code
        // Iterates through all members of the class and applies the matcher
        for (T member : getMembers(clazz)) {
            if (matcher.matches(member)) {
                matchedMembers.add(member);
            }
        }

        // Iterates through all members of the class including superclass
        if (matchedMembers.size() == 0) {
            for (T member : getMembers(clazz, true)) {
                if (matcher.matches(member)) {
                    matchedMembers.add(member);
                }
            }
        }

        return matchedMembers;
    }

}