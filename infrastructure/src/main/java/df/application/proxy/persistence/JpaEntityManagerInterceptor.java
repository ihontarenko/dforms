package df.application.proxy.persistence;

import org.jmouse.core.proxy.MethodInterceptor;
import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.MethodInvocationDecorator;
import org.jmouse.core.proxy.annotation.ProxyMethodInterceptor;
import jakarta.persistence.EntityManager;

import java.util.List;

@ProxyMethodInterceptor(value = EntityManager.class)
public class JpaEntityManagerInterceptor implements MethodInterceptor {

    private final static List<String> PERSISTENCE_METHODS = List.of("merge", "persist");

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationDecorator decorator  = new MethodInvocationDecorator(invocation);
        String                    methodName = decorator.getMethodName();

        if (PERSISTENCE_METHODS.contains(methodName)) {
            // modify entity before persist
            System.out.println(methodName);
        }

        return invocation.proceed();
    }

}
