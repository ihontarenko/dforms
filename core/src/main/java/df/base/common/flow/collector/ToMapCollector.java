package df.base.common.flow.collector;

import df.base.common.flow.Collector;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A collector that accumulates elements into a {@link Map}, where the keys and values
 * are determined by the provided functions.
 *
 * @param <K> the type of the keys
 * @param <V> the type of the values
 * @param <T> the type of the elements being collected
 * @see df.base.common.flow.Collectors#toMap(Function, Function)
 */
final public class ToMapCollector<K, V, T> implements Collector<T, Map<K, V>, Map<K, V>> {

    private final Function<T, K> keyMapper;
    private final Function<T, V> valueMapper;

    /**
     * Constructs a new collector with the given key and value mappers.
     *
     * @param keyMapper   the function to extract the key from each element
     * @param valueMapper the function to extract the value from each element
     */
    public ToMapCollector(Function<T, K> keyMapper, Function<T, V> valueMapper) {
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }

    /**
     * Provides the initial container (a new {@link HashMap}) to accumulate the elements.
     *
     * @return a new {@link HashMap} instance
     */
    @Override
    public Map<K, V> supplier() {
        return new HashMap<>();
    }

    /**
     * Adds the key-value pair to the accumulator map. The key is extracted using the
     * {@link #keyMapper}, and the value is extracted using the {@link #valueMapper}.
     *
     * @param accumulator the map that accumulates the key-value pairs
     * @param value       the element to be mapped to a key-value pair
     */
    @Override
    public void accumulator(Map<K, V> accumulator, T value) {
        accumulator.put(keyMapper.apply(value), valueMapper.apply(value));
    }

    /**
     * Returns the final accumulated result, which is the map of key-value pairs.
     *
     * @param accumulator the map containing the accumulated key-value pairs
     * @return the accumulated map of key-value pairs
     */
    @Override
    public Map<K, V> finisher(Map<K, V> accumulator) {
        return accumulator;
    }
}
