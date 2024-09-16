package df.base.common.interceptor;

import java.util.List;

public abstract class AbstractInterceptor<C extends ProcessingContext, T> implements Interceptor<C, T> {
    private final List<Processor<C, T>> processors;

    public AbstractInterceptor(List<Processor<C, T>> processors) {
        this.processors = processors;
    }

    @Override
    public void intercept(T target, C context) {
        for (Processor<C, T> processor : processors) {
            if (processor.supports(target, context)) {
                processor.process(target, context);
            }
        }
    }
}
