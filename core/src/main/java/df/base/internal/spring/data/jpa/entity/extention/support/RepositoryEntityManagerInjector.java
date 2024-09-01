package df.base.internal.spring.data.jpa.entity.extention.support;

import jakarta.persistence.EntityManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public class RepositoryEntityManagerInjector implements MethodInterceptor {

    static EntityManager proxy(EntityManager entityManager) {
        ProxyFactory factory = new ProxyFactory(entityManager);

        factory.addAdvice(new RepositoryEntityManagerInjector());

        return (EntityManager) factory.getProxy();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        // before invocation
        Object result = invocation.proceed();

        System.out.println("RepositoryEntityManagerInjector: " + invocation.getMethod().getDeclaringClass() + "#" + invocation.getMethod().getName());

        // after invocation

        return result;
    }

}
