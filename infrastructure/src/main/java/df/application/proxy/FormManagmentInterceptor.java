package df.application.proxy;

import df.application.service.form.FormManagementService;
import svit.proxy.MethodInterceptor;
import svit.proxy.MethodInvocation;
import svit.proxy.annotation.ProxyMethodInterceptor;

@ProxyMethodInterceptor(value = {FormManagementService.class})
public class FormManagmentInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
