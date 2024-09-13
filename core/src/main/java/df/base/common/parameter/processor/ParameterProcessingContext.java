package df.base.common.parameter.processor;

import df.base.common.processing.AbstractProcessingContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterProcessingContext extends AbstractProcessingContext {

    @Override
    protected void initialize() {
        addInterceptor(ParameterInterceptor.class,
                new ParameterInterceptor(List.of(new ParameterProcessor())));
        addInterceptor(ParametersInterceptor.class,
                new ParametersInterceptor(List.of(new ParametersProcessor())));
    }

}
