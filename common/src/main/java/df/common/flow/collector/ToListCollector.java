package df.common.flow.collector;

import df.common.flow.Collector;
import df.common.flow.Collectors;

import java.util.ArrayList;
import java.util.List;

/**
 * A collector that accumulates elements into a {@link List}.
 * This collector adds all elements to the list in the order they are encountered.
 *
 * @param <T> the type of the elements being collected
 * @see Collectors#toList()
 */
final public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    /**
     * Provides the initial container (a new {@link ArrayList}) to accumulate the elements.
     *
     * @return a new {@link ArrayList} instance
     */
    @Override
    public List<T> supplier() {
        return new ArrayList<>();
    }

    /**
     * Adds the given element to the accumulator list.
     * The elements are added in the order they are encountered.
     *
     * @param accumulator the list that accumulates the elements
     * @param value       the element to be added to the list
     */
    @Override
    public void accumulator(List<T> accumulator, T value) {
        accumulator.add(value);
    }

    /**
     * Returns the final accumulated result, which is the list of collected elements.
     *
     * @param accumulator the list containing the accumulated elements
     * @return the accumulated list of elements
     */
    @Override
    public List<T> finisher(List<T> accumulator) {
        return accumulator;
    }
}
