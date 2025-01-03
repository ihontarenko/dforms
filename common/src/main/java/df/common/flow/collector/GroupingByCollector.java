package df.common.flow.collector;

import df.common.flow.Collector;
import df.common.flow.Collectors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A collector that groups elements by a classifier function, and applies another collector
 * to each group of elements.
 *
 * @param <T> the type of the elements being collected
 * @param <K> the type of the key used for grouping
 * @param <R> the type of the result produced by the inner collector
 * @param <C> the type of the inner collector used to accumulate the elements of each group
 * @see Collectors#groupBy(Function)
 */
final public class GroupingByCollector<T, K, R, C extends Collector<T, R, R>>
        implements Collector<T, Map<K, R>, Map<K, R>> {

    private final Function<T, K> classifier;
    private final C collector;

    /**
     * Constructs a new {@link GroupingByCollector} with the given classifier and inner collector.
     *
     * @param classifier the function to classify elements into groups
     * @param collector  the collector used to accumulate elements in each group
     */
    public GroupingByCollector(Function<T, K> classifier, C collector) {
        this.classifier = classifier;
        this.collector = collector;
    }

    /**
     * Provides the initial container (a new {@link HashMap}) to accumulate the grouped elements.
     *
     * @return a new {@link HashMap} instance
     */
    @Override
    public Map<K, R> supplier() {
        return new HashMap<>();
    }

    /**
     * Adds the given element to the appropriate group in the accumulator map.
     * The element is classified by the {@link #classifier} and then accumulated
     * using the inner {@link #collector}.
     *
     * @param accumulator the map that holds the grouped elements
     * @param value       the element to be added to the correct group
     */
    @Override
    public void accumulator(Map<K, R> accumulator, T value) {
        K key = classifier.apply(value);
        // For each group, if it does not exist yet, we create a new group using the inner collector.
        collector.accumulator(accumulator.computeIfAbsent(key, k -> collector.supplier()), value);
    }

    /**
     * Returns the final accumulated result, which is the map of grouped elements.
     *
     * @param accumulator the map containing the accumulated grouped elements
     * @return the accumulated map of grouped elements
     */
    @Override
    public Map<K, R> finisher(Map<K, R> accumulator) {
        return accumulator;
    }
}
