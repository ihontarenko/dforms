package df.base.mapper;

public interface Mapper<S, D> {

    D map(S source);

    S reverse(D source);

    default void map(S source, D destination) {
        throw new UnsupportedOperationException();
    }

    default void reverse(D source, S destination) {
        throw new UnsupportedOperationException();
    }

}
