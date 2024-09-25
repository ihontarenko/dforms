package df.base.common.proxy;

public interface Interceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;

}
