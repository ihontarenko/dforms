package df.application.proxy;

import df.application.service.form.FormManagementService;
import org.jmouse.core.proxy.MethodInterceptor;
import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.Intercept;

@Intercept(value = {FormManagementService.class})
public class FormManagmentInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
