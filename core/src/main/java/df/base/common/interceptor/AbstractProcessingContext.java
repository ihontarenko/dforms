package df.base.common.interceptor;

import df.base.common.context.AbstractContext;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProcessingContext extends AbstractContext implements ProcessingContext {

    private final Map<Class<?>, Interceptor<?, ?>> interceptors = new HashMap<>();

    public AbstractProcessingContext() {
        initialize();
    }

    public <I extends Interceptor<?, ?>> void addInterceptor(Class<I> type, I interceptor) {
        interceptors.put(type, interceptor);
    }

    public <I extends Interceptor<?, ?>> I getInterceptor(Class<I> type) {
        return type.cast(interceptors.get(type));
    }

    abstract protected void initialize();

}
