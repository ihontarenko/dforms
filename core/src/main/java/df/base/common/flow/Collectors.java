package df.base.common.flow;

import df.base.common.flow.collector.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * A utility class that provides various collector implementations
 * for collecting elements into different types of collections such as
 * List, Set, and Map. This class is similar to {@link java.util.stream.Collectors},
 * but with custom collector implementations.
 */
final public class Collectors {

    /**
     * Returns a collector that groups the elements by a classifier function,
     * resulting in a map where the keys are the results of applying the classifier
     * function, and the values are lists of items that share the same key.
     *
     * @param classifier the function that classifies elements
     * @param <T>        the type of input elements
     * @param <K>        the type of the key
     * @return a collector that groups elements by the given classifier
     */
    public static <T, K> Collector<T, Map<K, List<T>>, Map<K, List<T>>> groupBy(Function<T, K> classifier) {
        return new GroupingByCollector<>(classifier, toList());
    }

    /**
     * Returns a collector that accumulates elements into a List.
     *
     * @param <T> the type of input elements
     * @return a collector that collects elements into a List
     */
    public static <T> Collector<T, List<T>, List<T>> toList() {
        return new ToListCollector<>();
    }

    /**
     * Returns a collector that accumulates elements into a Set.
     *
     * @param <T> the type of input elements
     * @return a collector that collects elements into a Set
     */
    public static <T> Collector<T, Set<T>, Set<T>> toSet() {
        return new ToSetCollector<>();
    }

    /**
     * Returns a collector that accumulates elements into a Map,
     * where the keys and values are determined by the provided functions.
     *
     * @param keyMapper   the function to extract the key from each element
     * @param valueMapper the function to extract the value from each element
     * @param <T>         the type of input elements
     * @param <K>         the type of the key
     * @param <V>         the type of the value
     * @return a collector that collects elements into a Map
     */
    public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return new ToMapCollector<>(keyMapper, valueMapper);
    }

    /**
     * Returns a collector that joins the elements of the flow into a single string,
     * using the provided delimiter, prefix, and postfix.
     *
     * @param delimiter the delimiter to insert between elements
     * @param prefix    the prefix to add at the beginning of the resulting string
     * @param postfix   the postfix to add at the end of the resulting string
     * @return a collector that joins the elements of the flow into a single string
     */
    public static <T> Collector<T, StringBuilder, String> joining(String delimiter, String prefix, String postfix) {
        return new ToStringCollector<>(delimiter, prefix, postfix);
    }

}
