package df.base.proxy.pipeline;

import df.base.common.pipeline.PipelineChain;
import df.base.common.proxy.MethodInterceptor;
import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.annotation.Interceptor;

@Interceptor(target = PipelineChain.class)
public class PipelineChainInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
