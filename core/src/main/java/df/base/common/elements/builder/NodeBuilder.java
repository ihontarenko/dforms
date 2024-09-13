package df.base.common.elements.builder;

import df.base.common.elements.Node;

public interface NodeBuilder<T> {
    Node build(T t, NodeBuilderContext ctx);
}
