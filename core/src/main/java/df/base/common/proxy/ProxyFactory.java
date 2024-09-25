package df.base.common.proxy;

public class ProxyFactory implements Proxy {

    private final ProxyConfig proxyConfig;

    public ProxyFactory(Object target) {
        this.proxyConfig = new ProxyConfig(target);
    }

    public void addInterceptor(Interceptor interceptor) {
        this.proxyConfig.addInterceptor(interceptor);
    }

    @Override
    public <T> T getProxy(ClassLoader classLoader) {
        Proxy proxy;

        if (proxyConfig.getInterfaces().isEmpty() && !proxyConfig.getTargetClass().isInterface()) {
            proxy = new CglibProxy(this.proxyConfig);
        } else {
            proxy = new JdkProxy(this.proxyConfig);
        }

        return proxy.getProxy(classLoader);
    }

    @Override
    public ProxyEngine getProxyEngine() {
        throw new UnsupportedOperationException("PROXY ENGINE IS UNKNOWN");
    }
}