package df.base.converter;

public interface Converter<T, R> {

    R convert(T source);

    T reverse(R source);

}
