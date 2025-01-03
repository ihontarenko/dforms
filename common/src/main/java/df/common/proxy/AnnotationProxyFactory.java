package df.common.proxy;

import df.common.proxy.annotation.Interceptor;
import df.common.container.ReflectionUtils;

public class AnnotationProxyFactory extends ProxyFactory {

    public AnnotationProxyFactory(Object target, Class<?>... baseClasses) {
        super(target);
        scanInterceptors(baseClasses);
    }

    public AnnotationProxyFactory(Object target) {
        super(target);
    }

    public void scanInterceptors(Class<?>... baseClasses) {
        for (Class<?> interceptorClass : InterceptorScanner.getMethodInterceptorClasses(baseClasses)) {
            Interceptor annotation = interceptorClass.getAnnotation(Interceptor.class);
            Class<?>[]  targets    = annotation.target();

            for (Class<?> targetClass : targets) {
                if (targetClass.isAssignableFrom(proxyConfig.getTargetClass())) {
                    MethodInterceptor interceptor = (MethodInterceptor) ReflectionUtils.instantiate(
                            ReflectionUtils.findFirstConstructor(interceptorClass));

                    addInterceptor(interceptor);
                }
            }
        }
    }

}