package df.base.common.validation.loader;

import df.base.common.validation.builder.Builder;

public class AbstractLoader<T> implements Builder<T, String> {

    @Override
    public T build() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T build(String source) {
        return null;
    }

}
