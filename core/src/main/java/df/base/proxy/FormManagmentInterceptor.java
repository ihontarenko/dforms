package df.base.proxy;

import df.base.common.proxy.MethodInterceptor;
import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.annotation.Interceptor;
import df.base.service.form.FormManagmentService;

@Interceptor(target = {FormManagmentService.class})
public class FormManagmentInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
