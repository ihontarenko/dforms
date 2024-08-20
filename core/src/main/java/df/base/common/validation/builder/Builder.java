package df.base.common.validation.builder;

public interface Builder<R, T> {

    R build();

    R build(T source);

}
