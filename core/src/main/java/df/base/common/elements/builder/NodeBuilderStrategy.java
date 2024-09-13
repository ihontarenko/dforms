package df.base.common.elements.builder;

public interface NodeBuilderStrategy {

    void setBuilder(Class<?> classType, NodeBuilder<?> builder);

    <T> NodeBuilder<T> getBuilder(Class<T> classType);

}
