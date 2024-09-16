package df.base.common.pipeline;

import df.base.common.pipeline.resolver.BasicParameterResolver;
import df.base.common.pipeline.resolver.SpelParameterResolver;
import df.base.common.pipeline.resolver.SubstitutionParameterResolver;

public class ResolversFactory {

    public ParameterResolver createResolver(Resolvers resolverType) {
        return switch (resolverType) {
            case BASIC -> new BasicParameterResolver();
            case SPEL -> new SpelParameterResolver();
            case SUBSTITUTE -> new SubstitutionParameterResolver();
        };
    }

}
