package df.common.flow;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Default implementation of the {@link Flow} interface, providing various collection operations
 * on an {@link Iterable} source.
 *
 * <p>This class allows performing operations like mapping, filtering, reducing, and collecting
 * elements into different collections (List, Set, Map), as well as operations like joining elements
 * and calculating sums, maxima, and minima.
 *
 * @param <T> the type of the elements in the flow
 */
public class DefaultFlow<T> implements Flow<T> {

    protected final Iterable<T> source;

    /**
     * Constructs a {@link DefaultFlow} instance based on the provided {@link Iterable}.
     *
     * @param source the source collection of elements
     */
    public DefaultFlow(Iterable<T> source) {
        this.source = source;
    }

    /**
     * Transforms each element of the flow using the provided mapperProvider function.
     *
     * @param mapper the function to apply to each element
     * @param <R> the result type of the transformation
     * @return a new {@link Flow} with the transformed elements
     */
    @Override
    public <R> Flow<R> map(Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : source) {
            result.add(mapper.apply(item));
        }
        return new DefaultFlow<>(result);
    }

    /**
     * Filters the elements of the flow based on the provided predicate.
     *
     * @param predicate the predicate to test each element
     * @return a new {@link Flow} containing only the elements that pass the predicate test
     */
    @Override
    public Flow<T> filter(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : source) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return new DefaultFlow<>(result);
    }

    /**
     * Reduces the elements of the flow to a single value using the provided reducer function.
     *
     * @param identity the initial value for the reduction
     * @param reducer  the function to apply to each element and the accumulated result
     * @param <R>      the type of the reduction result
     * @return the final reduced value
     */
    @Override
    public <R> R reduce(R identity, Function<T, R> reducer) {
        R result = identity;
        for (T item : source) {
            result = reducer.apply(item);
        }
        return result;
    }

    /**
     * Applies the provided consumer to each element in the flow.
     *
     * @param consumer the consumer to apply to each element
     */
    @Override
    public void each(Consumer<T> consumer) {
        for (T item : source) {
            consumer.accept(item);
        }
    }

    /**
     * Collects the elements of the flow into a {@link List}.
     *
     * @return a new {@link List} containing the elements of the flow
     */
    @Override
    public List<T> toList() {
        return collect(Collectors.toList());
    }

    /**
     * Collects the elements of the flow into a {@link Set}.
     *
     * @return a new {@link Set} containing the elements of the flow
     */
    @Override
    public Set<T> toSet() {
        return collect(Collectors.toSet());
    }

    /**
     * Collects the elements of the flow into a {@link Map} using the provided key and value mappers.
     *
     * @param keyMapper   the function to extract keys for the map
     * @param valueMapper the function to extract values for the map
     * @param <K>         the type of keys in the map
     * @param <V>         the type of values in the map
     * @return a new {@link Map} containing the elements of the flow
     */
    @Override
    public <K, V> Map<K, V> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * Groups the elements of the flow by a classifier function.
     *
     * @param classifier the function to classify elements into groups
     * @param <K>        the type of keys for grouping
     * @return a {@link Map} where keys are the results of applying the classifier function, and values are lists of elements
     */
    @Override
    public <K> Map<K, List<T>> groupBy(Function<T, K> classifier) {
        return collect(Collectors.groupBy(classifier));
    }

    /**
     * Collects the elements of the flow using the provided {@link Collector}.
     *
     * @param collector the collector used to accumulate the elements
     * @param <A>       the intermediate accumulation type
     * @param <R>       the final result type
     * @return the result of the collection
     */
    @Override
    public <R, A> R collect(Collector<T, A, R> collector) {
        A accumulator = collector.supplier();

        for (T item : source) {
            collector.accumulator(accumulator, item);
        }

        return collector.finisher(accumulator);
    }

    /**
     * Joins the elements of the flow into a single string, using the provided delimiter.
     *
     * @param delimiter the delimiter to insert between elements
     * @return a single string with the elements joined by the delimiter
     */
    @Override
    public String join(String delimiter) {
        return join(delimiter, null, null);
    }

    /**
     * Joins the elements of the flow into a single string, using the provided delimiter, prefix, and postfix.
     *
     * @param delimiter the delimiter to insert between elements
     * @param prefix    the prefix to add at the beginning of the resulting string
     * @param postfix   the postfix to add at the end of the resulting string
     * @return a single string with the elements joined by the delimiter
     */
    @Override
    public String join(String delimiter, String prefix, String postfix) {
        return collect(Collectors.joining(delimiter, prefix, postfix));
    }

    /**
     * Computes the sum of the elements in the flow, using the provided mapperProvider function.
     *
     * @param mapper the function to extract numeric values from the elements
     * @param <N>    the type of the result (should extend {@link Number})
     * @return the sum of the values
     */
    @Override
    public <N extends Number> N sum(Function<T, ? extends Number> mapper) {
        double sum = 0.0;

        for (T item : source) {
            sum += mapper.apply(item).doubleValue();
        }

        return (N) Double.valueOf(sum);
    }

    /**
     * Finds the maximum element in the flow based on the provided comparator.
     *
     * @param comparator the function to compare the elements
     * @param <C>        the type of the comparison key
     * @return the element with the maximum value according to the comparator
     */
    @Override
    public <C> T max(Function<T, Comparable<C>> comparator) {
        return minMax(comparator, true);
    }

    /**
     * Finds the minimum element in the flow based on the provided comparator.
     *
     * @param comparator the function to compare the elements
     * @param <C>        the type of the comparison key
     * @return the element with the minimum value according to the comparator
     */
    @Override
    public <C> T min(Function<T, Comparable<C>> comparator) {
        return minMax(comparator, false);
    }

    /**
     * Helper method to find the minimum or maximum element based on a comparator function.
     *
     * @param <C>        the type used for comparison
     * @param comparator a function to extract comparable values from the elements
     * @param minOrMax   {@code true} to find the maximum, {@code false} to find the minimum
     * @return the minimum or maximum element
     */
    private <C> T minMax(Function<T, Comparable<C>> comparator, boolean minOrMax) {
        Iterator<T> iterator = source.iterator();
        Predicate<Integer> predicate = minOrMax ? value -> value > 0 : value -> value < 0;

        if (!iterator.hasNext()) return null;

        T minMax = iterator.next();

        while (iterator.hasNext()) {
            T next = iterator.next();
            int compared = comparator.apply(next).compareTo((C) comparator.apply(minMax));

            if (predicate.test(compared)) {
                minMax = next;
            }
        }

        return minMax;
    }
}
