package df.application.proxy.pipeline;

import df.common.pipeline.PipelineProcessor;
import df.common.proxy.MethodInterceptor;
import df.common.proxy.MethodInvocation;
import df.common.proxy.annotation.Interceptor;

@Interceptor(target = PipelineProcessor.class)
public class PipelineProcessorInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
