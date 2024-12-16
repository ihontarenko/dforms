package df.base.common.flow.collector;

import df.base.common.flow.Collector;

import java.util.HashSet;
import java.util.Set;

/**
 * A collector that accumulates elements into a {@link Set}.
 * This collector ignores duplicates, as sets do not allow duplicate elements.
 *
 * @param <T> the type of the elements being collected
 * @see df.base.common.flow.Collectors#toSet()
 */
final public class ToSetCollector<T> implements Collector<T, Set<T>, Set<T>> {

    /**
     * Provides the initial container (a new {@link HashSet}) to accumulate the elements.
     *
     * @return a new {@link HashSet} instance
     */
    @Override
    public Set<T> supplier() {
        return new HashSet<>();
    }

    /**
     * Adds the given element to the accumulator set. If the element already exists in the set,
     * it will not be added again, since sets do not allow duplicates.
     *
     * @param accumulator the set that accumulates the elements
     * @param value       the element to be added to the set
     */
    @Override
    public void accumulator(Set<T> accumulator, T value) {
        accumulator.add(value);
    }

    /**
     * Returns the final accumulated result, which is the set of collected elements.
     *
     * @param accumulator the set containing the accumulated elements
     * @return the accumulated set of elements
     */
    @Override
    public Set<T> finisher(Set<T> accumulator) {
        return accumulator;
    }
}
