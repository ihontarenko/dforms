package df.application.proxy.pipeline;

import df.common.pipeline.PipelineProcessor;
import org.jmouse.core.proxy.MethodInterceptor;
import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.annotation.ProxyMethodInterceptor;

@ProxyMethodInterceptor(PipelineProcessor.class)
public class PipelineProcessorInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
