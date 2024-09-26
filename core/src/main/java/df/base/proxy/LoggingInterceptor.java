package df.base.proxy;

import df.base.common.proxy.MethodInterceptor;
import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.annotation.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor(target = Object.class)
public class LoggingInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LOGGER.info(">>> BEFORE: [PROXY][{}]: METHOD: {}", invocation.getTarget().getClass(), invocation.getMethod().getName());
        Object result = invocation.proceed();
        LOGGER.info(">>> AFTER: [PROXY][{}]: METHOD: {}", invocation.getTarget().getClass(), invocation.getMethod().getName());

        return result;
    }



}
