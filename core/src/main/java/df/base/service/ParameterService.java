package df.base.service;

import df.base.common.parameter.processor.ParameterProcessingContext;
import df.base.common.parameter.processor.ParametersInterceptor;
import df.base.common.processing.ProcessingContext;
import df.base.dto.NestedKeyValue;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {

    private final ProcessingContext processingContext;

    public ParameterService(ParameterProcessingContext processingContext) {
        this.processingContext = processingContext;
    }

    public void processParameters(Iterable<NestedKeyValue> values) {
        processingContext.getInterceptor(ParametersInterceptor.class)
                .intercept(values, (ParameterProcessingContext) processingContext);
    }

}
