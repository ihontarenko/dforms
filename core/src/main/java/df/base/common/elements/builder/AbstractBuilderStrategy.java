package df.base.common.elements.builder;

import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderStrategy;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractBuilderStrategy implements NodeBuilderStrategy {

    private final Map<Class<?>, NodeBuilder<?>> builders = new HashMap<>();

    abstract protected void initialize();

    @Override
    public void setBuilder(Class<?> classType, NodeBuilder<?> builder) {
        this.builders.put(classType, builder);
    }

    @Override
    public <T> NodeBuilder<T> getBuilder(Class<T> classType) {
        return (NodeBuilder<T>) builders.get(classType);
    }

}
