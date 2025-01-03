package df.common.flow.collector;

import df.common.flow.Collector;

/**
 * A collector that concatenates input elements into a single {@link String},
 * separated by a specified delimiter, and wrapped with a prefix and a postfix.
 *
 * Example usage of {@code ToStringCollector}:
 * <pre>{@code
 * List<String> items = List.of("apple", "banana", "cherry");
 * ToStringCollector<String> collector = new ToStringCollector<>(", ", "[", "]");
 * String result = items.stream().collect(collector);
 * System.out.println(result); // Output: [apple, banana, cherry]
 * }</pre>
 *
 * @param <T> the type of input elements to be collected
 */
public class ToStringCollector<T> implements Collector<T, StringBuilder, String> {

    private final String delimiter;
    private final String prefix;
    private final String postfix;

    /**
     * Constructs a {@code ToStringCollector} with the specified delimiter, prefix, and postfix.
     *
     * @param delimiter the delimiter to separate elements in the resulting string
     * @param prefix    the prefix to add at the beginning of the resulting string
     * @param postfix   the postfix to add at the end of the resulting string
     */
    public ToStringCollector(String delimiter, String prefix, String postfix) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    /**
     * Provides a new {@link StringBuilder} initialized with the prefix.
     *
     * @return a new {@link StringBuilder} with the prefix appended
     */
    @Override
    public StringBuilder supplier() {
        return new StringBuilder();
    }

    /**
     * Accumulates the given value into the {@link StringBuilder}.
     * Adds the delimiter before appending the value, if it is not the first element.
     *
     * @param accumulator the {@link StringBuilder} used to accumulate the results
     * @param value       the value to be added to the accumulator
     */
    @Override
    public void accumulator(StringBuilder accumulator, T value) {
        if (accumulator.length() > 0) {
            accumulator.append(delimiter);
        }
        accumulator.append(value);
    }

    /**
     * Finalizes the accumulation by adding the prefix and postfix and converting to a string.
     *
     * @param accumulator the {@link StringBuilder} containing the accumulated values
     * @return the resulting string with prefix, delimiter-separated elements, and postfix
     */
    @Override
    public String finisher(StringBuilder accumulator) {
        return (prefix == null ? "" : prefix) + accumulator.toString() + (postfix == null ? "" : postfix);
    }
}
