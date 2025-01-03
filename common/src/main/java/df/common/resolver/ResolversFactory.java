package df.common.resolver;

public class ResolversFactory {

    public AbstractValueResolver createResolver(Resolvers resolverType) {
        return switch (resolverType) {
            case BASIC -> new BasicParameterResolver();
            case SPEL -> new SpelParameterResolver();
            case SUBSTITUTE -> new SubstitutionParameterResolver();
        };
    }

}
