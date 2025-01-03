package df.common.elements.builder;

import df.common.elements.Node;

public interface NodeBuilder<T> {
    Node build(T t, NodeBuilderContext ctx);
}
