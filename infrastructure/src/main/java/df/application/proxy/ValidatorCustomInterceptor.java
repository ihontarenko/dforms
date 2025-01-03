package df.application.proxy;

import df.common.proxy.MethodInterceptor;
import df.common.proxy.MethodInvocation;
import df.common.proxy.annotation.Interceptor;
import df.common.validation.custom.Validator;

@Interceptor(target = Validator.class)
public class ValidatorCustomInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
