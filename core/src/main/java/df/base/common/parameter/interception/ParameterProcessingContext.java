package df.base.common.parameter.interception;

import df.base.common.interceptor.AbstractProcessingContext;
import df.base.common.support.spel.SpelEvaluator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterProcessingContext extends AbstractProcessingContext {

    public ParameterProcessingContext(SpelEvaluator spelEvaluator) {
        super();
        setProperty(SpelEvaluator.class, spelEvaluator);
    }

    @Override
    protected void initialize() {
        addInterceptor(ParameterInterceptor.class,
                new ParameterInterceptor(List.of(new ParameterProcessor())));
        addInterceptor(ParametersInterceptor.class,
                new ParametersInterceptor(List.of(new ParametersProcessor())));
    }

}
