package df.base.common.annotation.interception;

import df.base.common.interceptor.AbstractInterceptor;
import df.base.common.interceptor.Processor;
import df.base.dto.NestedKeyValue;

import java.util.List;

public class ParametersInterceptor extends AbstractInterceptor<ParameterProcessingContext, Iterable<NestedKeyValue>> {

    public ParametersInterceptor(List<Processor<ParameterProcessingContext, Iterable<NestedKeyValue>>> processors) {
        super(processors);
    }

}
