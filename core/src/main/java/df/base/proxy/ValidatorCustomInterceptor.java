package df.base.proxy;

import df.base.common.proxy.MethodInterceptor;
import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.annotation.Interceptor;
import df.base.common.validation.custom.Validator;

@Interceptor(target = Validator.class)
public class ValidatorCustomInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
