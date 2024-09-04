package df.base.common.libs.confio;

public interface ComponentResolver<R> {

    void configure(R resolver);

    Object resolve(String name);

}
