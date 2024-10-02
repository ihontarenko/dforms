package df.base.common.invocable;

import df.base.common.matcher.Matcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static df.base.common.matcher.reflection.TypeMatchers.isSimilar;
import static df.base.common.matcher.reflection.TypeMatchers.isSubtype;

abstract public class AbstractInvocable implements Invocable {

    protected final MethodDescriptor      descriptor;
    protected final List<MethodParameter> parameters;

    public AbstractInvocable(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        descriptor = new ReflectionClassTypeDescriptor(clazz).getMethod(methodName, parameterTypes);
        parameters = new ArrayList<>();
    }

    public AbstractInvocable(Method method) {
        descriptor = new ReflectionMethodDescriptor(method, method.getDeclaringClass());
        parameters = new ArrayList<>();
    }

    @Override
    public void addParameter(MethodParameter parameter) {
        parameters.add(parameter);
    }

    @Override
    public MethodDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public Collection<? extends MethodParameter> getParameters() {
        return parameters;
    }

    @Override
    public Object[] getPreparedParameters() {
        Object[] parameters = new Object[this.parameters.size()];

        for (MethodParameter argument : this.parameters) {
            parameters[argument.getIndex()] = argument.getValue();
        }

        return parameters;
    }

    protected void validateParameters() {
        // throw exception if count of passed parameters mismatched with expected
        if (descriptor.getParametersCount() != parameters.size()) {
            throw new ParameterCountException("The method expected %d parameters, but %d were passed."
                    .formatted(descriptor.getParametersCount(), parameters.size()));
        }

        // expected parameters types
        Class<?>[] parametersTypes = descriptor.getParameterTypes();

        // check on matching passed arguments with expected
        for (MethodParameter parameter : parameters) {
            Class<?>          expectedType = parametersTypes[parameter.getIndex()];
            Matcher<Class<?>> softChecker  = isSimilar(expectedType).or(isSubtype(expectedType));

            if (!softChecker.matches(parameter.getDescriptor().getNativeClass(), null)) {
                throw new ParameterTypeException("Expected type '%s' for argument %d in method '%s', but got '%s'."
                        .formatted(parametersTypes[parameter.getIndex()], parameter.getIndex(), descriptor.getName(), parameter.getDescriptor().getNativeClass()));
            }
        }

    }

}