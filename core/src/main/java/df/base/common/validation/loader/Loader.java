package df.base.common.validation.loader;

public interface Loader<T, S> {

    T load(S source);

}
