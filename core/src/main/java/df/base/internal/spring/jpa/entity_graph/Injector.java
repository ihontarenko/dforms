package df.base.internal.spring.jpa.entity_graph;

public interface Injector<S, D> {

    void inject(S source, D destination);

}
