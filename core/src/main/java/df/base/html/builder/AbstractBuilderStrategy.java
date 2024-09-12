package df.base.html.builder;

import df.base.common.elements.builder.Builder;
import df.base.common.elements.builder.BuilderStrategy;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractBuilderStrategy implements BuilderStrategy {

    private Map<Class<?>, Builder<?>> builders = new HashMap<>();

    abstract protected void initialize();

    @Override
    public void setBuilder(Class<?> classType, Builder<?> builder) {
        this.builders.put(classType, builder);
    }

    @Override
    public <T> Builder<T> getBuilder(Class<T> classType) {
        return (Builder<T>) builders.get(classType);
    }

}
