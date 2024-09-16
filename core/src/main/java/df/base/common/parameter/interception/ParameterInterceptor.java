package df.base.common.parameter.interception;

import df.base.common.interceptor.AbstractInterceptor;
import df.base.common.interceptor.Processor;
import df.base.dto.NestedKeyValue;

import java.util.List;

public class ParameterInterceptor extends AbstractInterceptor<ParameterProcessingContext, NestedKeyValue> {
    public ParameterInterceptor(List<Processor<ParameterProcessingContext, NestedKeyValue>> processors) {
        super(processors);
    }
}
