package df.application.proxy.persistence;

import org.jmouse.core.proxy.MethodInterceptor;
import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.annotation.ProxyMethodInterceptor;
import jakarta.persistence.Query;

@ProxyMethodInterceptor(value = Query.class)
public class JpaQueryInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
