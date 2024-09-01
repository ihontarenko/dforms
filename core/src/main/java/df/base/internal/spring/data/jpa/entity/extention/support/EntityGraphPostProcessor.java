package df.base.internal.spring.data.jpa.entity.extention.support;

import jakarta.persistence.EntityManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class EntityGraphPostProcessor implements MethodInterceptor {

    private final EntityManager em;
    private final Class<?> domainClass;

    public EntityGraphPostProcessor(EntityManager em, Class<?> domainClass) {
        this.em = em;
        this.domainClass = domainClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        if (invocation.getMethod().getName().startsWith("find")) {
            System.out.println("POST PROCESSOR: " + invocation.getMethod());
        }

        return invocation.proceed();
    }

}
