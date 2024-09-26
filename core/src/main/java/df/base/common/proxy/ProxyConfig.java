package df.base.common.proxy;

import df.base.common.libs.jbm.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProxyConfig {

    private final Object            target;
    private final Class<?>          targetClass;
    private final List<Class<?>>          interfaces;
    private final List<MethodInterceptor> interceptors = new ArrayList<>();

    public ProxyConfig(Object target) {
        this.target = Objects.requireNonNull(target);
        this.targetClass = target.getClass();
        this.interfaces = List.of(ReflectionUtils.getClassInterfaces(targetClass));
    }

    public Object getTarget() {
        return target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public List<Class<?>> getInterfaces() {
        return interfaces;
    }

    public void addInterceptor(MethodInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public List<MethodInterceptor> getInterceptors() {
        return List.copyOf(interceptors);
    }

}
