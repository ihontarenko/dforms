package df.base.common.parameter.interception;

import df.base.common.interceptor.Processor;
import df.base.dto.NestedKeyValue;

import java.util.Objects;

import static java.lang.System.out;

public class ParameterProcessor implements Processor<ParameterProcessingContext, NestedKeyValue> {

    @Override
    public void process(NestedKeyValue target, ParameterProcessingContext context) {
        out.printf("[%s]: %s -> %s:%s%n", target.getClass().getName(), getClass().getSimpleName(),
                   target.getKey(), target.getValue());
    }

    @Override
    public boolean supports(NestedKeyValue target, ParameterProcessingContext context) {
        return Objects.nonNull(target.getValue());
    }

}
