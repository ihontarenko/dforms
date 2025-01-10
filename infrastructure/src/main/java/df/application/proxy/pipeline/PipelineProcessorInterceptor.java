package df.application.proxy.pipeline;

import df.common.pipeline.PipelineProcessor;
import svit.proxy.MethodInterceptor;
import svit.proxy.MethodInvocation;
import svit.proxy.annotation.ProxyMethodInterceptor;

@ProxyMethodInterceptor(PipelineProcessor.class)
public class PipelineProcessorInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
