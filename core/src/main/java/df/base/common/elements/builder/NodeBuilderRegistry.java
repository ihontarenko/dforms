package df.base.common.elements.builder;

public interface NodeBuilderRegistry {

    <T> void setBuilder(Class<T> classType, NodeBuilder<? extends T> builder);

    <T> NodeBuilder<T> getBuilder(Class<T> classType);

}