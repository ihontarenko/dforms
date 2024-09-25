package df.base.common.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProxyConfig {

    private final Object            target;
    private final Class<?>          targetClass;
    private final List<Class<?>>    interfaces;
    private final List<Interceptor> interceptors = new ArrayList<>();

    public ProxyConfig(Object target) {
        this.target = Objects.requireNonNull(target);
        this.targetClass = target.getClass();
        this.interfaces = List.of(targetClass.getInterfaces());
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

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public List<Interceptor> getInterceptors() {
        return List.copyOf(interceptors);
    }

}
