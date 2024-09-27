package df.base.proxy.persistence;

import df.base.common.proxy.MethodInterceptor;
import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.annotation.Interceptor;
import jakarta.persistence.Query;

@Interceptor(target = Query.class)
public class JpaQueryInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        System.out.println(invocation.getTarget().getClass() + "#" + invocation.getMethod().getName());

        return invocation.proceed();
    }

}
