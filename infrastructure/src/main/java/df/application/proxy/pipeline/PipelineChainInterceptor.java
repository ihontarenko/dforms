package df.application.proxy.pipeline;

import df.common.pipeline.PipelineChain;
import df.common.proxy.MethodInterceptor;
import df.common.proxy.MethodInvocation;
import df.common.proxy.annotation.Interceptor;

@Interceptor(target = PipelineChain.class)
public class PipelineChainInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
