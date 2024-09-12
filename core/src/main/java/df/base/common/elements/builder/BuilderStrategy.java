package df.base.common.elements.builder;

public interface BuilderStrategy {

    void setBuilder(Class<?> classType, Builder<?> builder);

    <T> Builder<T> getBuilder(Class<T> classType);

}
