package df.base.common.parameter.processor;

import df.base.common.processing.AbstractProcessingContext;
import df.base.dto.NestedKeyValue;

public class ParameterProcessingContext extends AbstractProcessingContext<NestedKeyValue> {

    public ParameterProcessingContext(NestedKeyValue target) {
        super(target);
    }

}
