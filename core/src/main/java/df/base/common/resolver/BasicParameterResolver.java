package df.base.common.resolver;

public class BasicParameterResolver extends AbstractValueResolver {

    @Override
    public Object resolve(Object value, ResolverContext context) {
        return value;
    }

}
