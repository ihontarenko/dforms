package df.base.common.parameter.processor;

import df.base.common.processing.AbstractInterceptor;
import df.base.common.processing.Processor;
import df.base.dto.NestedKeyValue;

import java.util.List;

public class ParametersInterceptor extends AbstractInterceptor<ParameterProcessingContext, Iterable<NestedKeyValue>> {

    public ParametersInterceptor(List<Processor<ParameterProcessingContext, Iterable<NestedKeyValue>>> processors) {
        super(processors);
    }

}
