package df.application.proxy.persistence;

import svit.proxy.MethodInterceptor;
import svit.proxy.MethodInvocation;
import svit.proxy.MethodInvocationDecorator;
import svit.proxy.annotation.ProxyMethodInterceptor;
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
