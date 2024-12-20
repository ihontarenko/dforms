package df.base.common.mapping;

import static df.base.common.reflection.Reflections.getInterfacesParameterizedType;

public interface Mapper<S, D> {

    D map(S source);

    S reverse(D source);

    default void map(S source, D destination) {
        throw new UnsupportedOperationException();
    }

    default void reverse(D source, S destination) {
        throw new UnsupportedOperationException();
    }

    default boolean applicable(Object source) {
        return getInterfacesParameterizedType(getClass(), Mapper.class, 0)
                .map(type -> type.equals(source.getClass())).orElse(true);
    }

}
