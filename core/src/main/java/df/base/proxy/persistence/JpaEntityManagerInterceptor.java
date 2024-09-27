package df.base.proxy.persistence;

import df.base.common.proxy.MethodInterceptor;
import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.MethodInvocationDecorator;
import df.base.common.proxy.annotation.Interceptor;
import jakarta.persistence.EntityManager;

import java.util.List;

@Interceptor(target = EntityManager.class)
public class JpaEntityManagerInterceptor implements MethodInterceptor {

    private final static List<String> PERSISTENCE_METHODS = List.of("merge", "persist");

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationDecorator decorator  = new MethodInvocationDecorator(invocation);
        String                    methodName = decorator.getMethodName();

        if (PERSISTENCE_METHODS.contains(methodName)) {
            // modify entity before persist
        }

        return invocation.proceed();
    }

}
