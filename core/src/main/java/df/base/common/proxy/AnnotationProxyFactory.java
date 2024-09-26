package df.base.common.proxy;

import df.base.common.proxy.annotation.Interceptor;

import static df.base.common.libs.jbm.ReflectionUtils.findFirstConstructor;
import static df.base.common.libs.jbm.ReflectionUtils.instantiate;

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
                    MethodInterceptor interceptor = (MethodInterceptor) instantiate(
                            findFirstConstructor(interceptorClass));

                    addInterceptor(interceptor);
                }
            }
        }
    }

}