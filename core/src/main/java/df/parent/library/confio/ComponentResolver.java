package df.parent.library.confio;

public interface ComponentResolver<R> {

    void configure(R resolver);

    Object resolve(String name);

}
