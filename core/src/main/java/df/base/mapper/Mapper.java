package df.base.mapper;

public interface Mapper<T, R> {

    R map(T source);

    T reverse(R source);

}
