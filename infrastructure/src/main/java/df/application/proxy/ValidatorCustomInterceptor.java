package df.application.proxy;

import org.jmouse.core.proxy.MethodInterceptor;
import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.annotation.ProxyMethodInterceptor;
import df.common.validation.custom.Validator;

@ProxyMethodInterceptor(value = Validator.class)
public class ValidatorCustomInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
