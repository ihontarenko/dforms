package df.base.common.annotation.interception;

import df.base.common.interceptor.CompositeInterceptor;
import df.base.common.interceptor.Processor;
import df.base.dto.NestedKeyValue;

import java.util.List;

public class ParametersInterceptor extends CompositeInterceptor<ParameterProcessingContext, Iterable<NestedKeyValue>> {

    public ParametersInterceptor(List<Processor<ParameterProcessingContext, Iterable<NestedKeyValue>>> processors) {
        super(processors);
    }

}
