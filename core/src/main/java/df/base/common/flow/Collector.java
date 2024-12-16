package df.base.common.flow;

public interface Collector<T, A, R> {

    A supplier();

    void accumulator(A accumulator, T value);

    R finisher(A accumulator);
}
