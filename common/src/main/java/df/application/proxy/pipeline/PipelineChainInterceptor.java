package df.application.proxy.pipeline;

import org.jmouse.pipeline.PipelineChain;
import org.jmouse.core.proxy.MethodInterceptor;
import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.Intercept;

@Intercept(value = PipelineChain.class)
public class PipelineChainInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
