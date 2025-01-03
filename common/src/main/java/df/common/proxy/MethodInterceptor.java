package df.common.proxy;

public interface MethodInterceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;

}
