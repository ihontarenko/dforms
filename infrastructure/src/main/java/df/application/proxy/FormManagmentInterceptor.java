package df.application.proxy;

import df.application.service.form.FormManagementService;
import df.common.proxy.MethodInterceptor;
import df.common.proxy.MethodInvocation;
import df.common.proxy.annotation.Interceptor;

@Interceptor(target = {FormManagementService.class})
public class FormManagmentInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
