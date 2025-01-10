package df.application.proxy.persistence;

import svit.proxy.MethodInterceptor;
import svit.proxy.MethodInvocation;
import svit.proxy.annotation.ProxyMethodInterceptor;
import jakarta.persistence.Query;

@ProxyMethodInterceptor(value = Query.class)
public class JpaQueryInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
