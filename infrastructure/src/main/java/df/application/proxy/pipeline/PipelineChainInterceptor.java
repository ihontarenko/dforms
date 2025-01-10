package df.application.proxy.pipeline;

import df.common.pipeline.PipelineChain;
import svit.proxy.MethodInterceptor;
import svit.proxy.MethodInvocation;
import svit.proxy.annotation.ProxyMethodInterceptor;

@ProxyMethodInterceptor(value = PipelineChain.class)
public class PipelineChainInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
