package df.common.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InterceptorsChainExecutor<T> implements ChainExecutor<T> {

    private final List<Interceptor<T>> interceptors = new ArrayList<>();
    private final Consumer<T>          handler;
    private       int                  current      = -1;

    public InterceptorsChainExecutor(Consumer<T> handler, Interceptor<T>... interceptors) {
        this.handler = handler;
        this.interceptors.addAll(List.of(interceptors));
    }

    @Override
    public void proceed(T target) {
        Interceptor<T> interceptor;

        if (interceptors.size() > ++current) {
            interceptor = interceptors.get(current);

            interceptor.intercept(target, this);
        }

        handler.accept(target);
    }

}
