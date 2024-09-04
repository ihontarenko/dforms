package df.base.common.spring.jpa.entity_graph;

public interface Injector<S, D> {

    void inject(S source, D destination);

}
