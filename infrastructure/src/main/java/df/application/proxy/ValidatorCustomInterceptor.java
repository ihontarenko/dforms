package df.application.proxy;

import svit.proxy.MethodInterceptor;
import svit.proxy.MethodInvocation;
import svit.proxy.annotation.ProxyMethodInterceptor;
import df.common.validation.custom.Validator;

@ProxyMethodInterceptor(value = Validator.class)
public class ValidatorCustomInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
