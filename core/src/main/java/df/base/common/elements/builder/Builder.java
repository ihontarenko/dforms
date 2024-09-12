package df.base.common.elements.builder;

import df.base.common.elements.Node;

public interface Builder<T> {
    Node build(T t, BuilderContext ctx);
}
