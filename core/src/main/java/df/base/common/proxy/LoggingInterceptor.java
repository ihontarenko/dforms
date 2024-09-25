package df.base.common.proxy;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Before method: " + invocation.getMethod().getName());
        Object result = invocation.proceed();
        System.out.println("After method: " + invocation.getMethod().getName());

        result = result + "]";

        return result;
    }

}
