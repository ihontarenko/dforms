package df.application.proxy.persistence;

import df.common.proxy.MethodInterceptor;
import df.common.proxy.MethodInvocation;
import df.common.proxy.annotation.Interceptor;
import jakarta.persistence.Query;

@Interceptor(target = Query.class)
public class JpaQueryInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
