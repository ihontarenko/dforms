package df.base.common.pipeline.resolver;

import df.base.common.pipeline.ParameterResolver;

public class SubstitutionParameterResolver extends ParameterResolver {

    @Override
    public Object resolve(Object value) {
        return value;
    }

}