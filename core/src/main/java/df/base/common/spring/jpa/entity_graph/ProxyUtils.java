package df.base.common.spring.jpa.entity_graph;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;

final public class ProxyUtils {

    private ProxyUtils() { }

    public static <T> T proxy(Object target, MethodInterceptor interceptor) {
        ProxyFactory factory = new ProxyFactory(target);
        factory.addAdvice(interceptor);
        return (T) factory.getProxy();
    }

}
