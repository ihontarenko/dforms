package df.base.internal.spring.jpa.entity_graph;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class EntityManagerProxy implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();

        System.out.println("EM PROXY: " + invocation.getThis().getClass() + "#" + invocation.getMethod().getName());

        return result;
    }

}
