package df.base.common.flow;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A functional interface for working with sequences of elements, enabling functional-style operations
 * such as mapping, filtering, reducing, and collecting.
 *
 * <p>The {@code Flow} interface provides a declarative way to process collections of elements in a
 * fluent and composable manner, similar to Java Streams. This interface supports transformations,
 * aggregations, and various terminal operations.</p>
 *
 * Example usage of the {@code Flow} interface:
 *
 * <pre>{@code
 * List<String> names = List.of("Alice", "Bob", "Charlie", "David");
 * Flow<String> flow = Flow.of(names);
 *
 * String result = flow.filter(name -> name.startsWith("A"))
 *                     .map(String::toUpperCase)
 *                     .join(", ");
 *
 * System.out.println(result); // Output: "ALICE"
 * }</pre>
 *
 * @param <T> the type of elements in the {@code Flow}
 */
public interface Flow<T> {

    /**
     * Transforms the elements of this flow using the specified mapping function.
     *
     * @param <R>    the type of the elements in the resulting flow
     * @param mapper a function to apply to each element
     * @return a new {@code Flow} containing the transformed elements
     */
    <R> Flow<R> map(Function<T, R> mapper);

    /**
     * Filters the elements of this flow based on the specified predicate.
     *
     * @param predicate a predicate to apply to each element
     * @return a new {@code Flow} containing only the elements that match the predicate
     */
    Flow<T> filter(Predicate<T> predicate);

    /**
     * Reduces the elements of this flow into a single result by repeatedly applying the specified reducer function.
     *
     * @param <R>     the type of the resulting value
     * @param identity the initial value for the reduction
     * @param reducer  a function to combine elements
     * @return the reduced result
     */
    <R> R reduce(R identity, Function<T, R> reducer);

    /**
     * Performs the specified action for each element in this flow.
     *
     * @param consumer an action to perform on each element
     */
    void each(Consumer<T> consumer);

    /**
     * Collects the elements of this flow into a {@link List}.
     *
     * @return a list containing all elements of this flow
     */
    List<T> toList();

    /**
     * Collects the elements of this flow into a {@link Set}.
     *
     * @return a set containing all elements of this flow
     */
    Set<T> toSet();

    /**
     * Collects the elements of this flow into a {@link Map} using the specified key and value mapping functions.
     *
     * @param <K>        the type of map keys
     * @param <V>        the type of map values
     * @param keyMapper   a function to generate keys for the map
     * @param valueMapper a function to generate values for the map
     * @return a map containing the elements of this flow
     */
    <K, V> Map<K, V> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper);

    /**
     * Collects the elements of this flow using a custom collector.
     *
     * @param <R>       the type of the resulting value
     * @param <A>       the intermediate accumulation type of the collector
     * @param collector a {@link Collector} to handle the elements of this flow
     * @return the collected result
     */
    <R, A> R collect(Collector<T, A, R> collector);

    /**
     * Groups the elements of this flow into a map based on the specified classifier function.
     *
     * @param <K>        the type of map keys
     * @param classifier a function to classify elements into groups
     * @return a map where keys represent groups and values are lists of elements in those groups
     */
    <K> Map<K, List<T>> groupBy(Function<T, K> classifier);

    /**
     * Joins the elements of this flow into a single string with the specified delimiter.
     *
     * @param delimiter the delimiter to use between elements
     * @return a string representation of the elements, separated by the delimiter
     */
    String join(String delimiter);

    /**
     * Joins the elements of this flow into a single string with the specified delimiter, prefix, and postfix.
     *
     * @param delimiter the delimiter to use between elements
     * @param prefix    the prefix to prepend to the resulting string
     * @param postfix   the postfix to append to the resulting string
     * @return a string representation of the elements, with the specified formatting
     */
    String join(String delimiter, String prefix, String postfix);

    /**
     * Computes the sum of the elements of this flow, as specified by the mapping function.
     *
     * @param <N>    the type of the numeric result
     * @param mapper a function to extract numeric values from the elements
     * @return the sum of the mapped values
     */
    <N extends Number> N sum(Function<T, ? extends Number> mapper);

    /**
     * Finds the maximum element of this flow based on the specified comparator.
     *
     * @param <C>        the type used for comparison
     * @param comparator a function to extract comparable values from the elements
     * @return the maximum element
     */
    <C> T max(Function<T, Comparable<C>> comparator);

    /**
     * Finds the minimum element of this flow based on the specified comparator.
     *
     * @param <C>        the type used for comparison
     * @param comparator a function to extract comparable values from the elements
     * @return the minimum element
     */
    <C> T min(Function<T, Comparable<C>> comparator);

    /**
     * Creates a {@code Flow} from the specified source iterable.
     *
     * @param <T>    the type of elements in the source iterable
     * @param source the source iterable
     * @return a new {@code Flow} instance wrapping the source iterable
     */
    static <T> Flow<T> of(Iterable<T> source) {
        return new DefaultFlow<>(source);
    }

}

