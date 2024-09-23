package df.base.common.annotation.interception;

import df.base.common.interceptor.CompositeInterceptor;
import df.base.common.interceptor.Processor;
import df.base.dto.NestedKeyValue;

import java.util.List;

public class ParameterInterceptor extends CompositeInterceptor<ParameterProcessingContext, NestedKeyValue> {
    public ParameterInterceptor(List<Processor<ParameterProcessingContext, NestedKeyValue>> processors) {
        super(processors);
    }
}
