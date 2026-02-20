package df.application.proxy;

import org.jmouse.core.proxy.MethodInterceptor;
import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.Intercept;
import org.jmouse.validator.old.Validator;

@Intercept(value = Validator.class)
public class ValidatorCustomInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
