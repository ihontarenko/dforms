package df.base.internal.confio;

public interface ComponentResolver<R> {

    void configure(R resolver);

    Object resolve(String name);

}
