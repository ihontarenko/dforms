package df.base.common.parameter.processor;

import df.base.common.processing.AbstractInterceptor;
import df.base.common.processing.Processor;
import df.base.dto.NestedKeyValue;

import java.util.List;

public class ParameterInterceptor extends AbstractInterceptor<ParameterProcessingContext, NestedKeyValue> {
    public ParameterInterceptor(List<Processor<ParameterProcessingContext, NestedKeyValue>> processors) {
        super(processors);
    }
}
