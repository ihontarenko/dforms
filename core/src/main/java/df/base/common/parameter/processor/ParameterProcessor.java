package df.base.common.parameter.processor;

import df.base.common.processing.Processor;
import df.base.dto.NestedKeyValue;

import java.util.Objects;

public class ParameterProcessor implements Processor<ParameterProcessingContext, NestedKeyValue> {

    @Override
    public void process(NestedKeyValue target, ParameterProcessingContext context) {
        System.out.println(getClass().getSimpleName() + " -> " + target.getKey() +":"+ target.getValue());
    }

    @Override
    public boolean supports(NestedKeyValue target, ParameterProcessingContext context) {
        return Objects.nonNull(target.getValue());
    }

}
