package df.base.common.parameter.processor;

import df.base.common.processing.Processor;
import df.base.dto.NestedKeyValue;

import java.util.List;
import java.util.Objects;

public class ParametersProcessor implements Processor<ParameterProcessingContext, Iterable<NestedKeyValue>> {

    @Override
    public void process(Iterable<NestedKeyValue> target, ParameterProcessingContext context) {
        for (NestedKeyValue value : target) {
            context.getInterceptor(ParameterInterceptor.class).intercept(value, context);
        }
    }

    @Override
    public boolean supports(Iterable<NestedKeyValue> target, ParameterProcessingContext context) {
        return target != null;
    }

}
