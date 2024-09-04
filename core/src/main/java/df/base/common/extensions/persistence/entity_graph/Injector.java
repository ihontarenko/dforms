package df.base.common.extensions.persistence.entity_graph;

public interface Injector<S, D> {

    void inject(S source, D destination);

}
