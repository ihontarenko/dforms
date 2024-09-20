package df.base.common.interceptor;

import df.base.common.context.Context;

public interface ProcessingContext extends Context {

    <I extends Interceptor<?, ?>> void addInterceptor(Class<I> type, I interceptor);

    <I extends Interceptor<?, ?>> I getInterceptor(Class<I> type);

}
