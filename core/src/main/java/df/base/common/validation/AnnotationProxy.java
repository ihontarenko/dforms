package df.base.common.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class AnnotationProxy implements InvocationHandler {

    private final Map<String, Object>         parameters;
    private final Class<? extends Annotation> annotationType;

    public AnnotationProxy(Class<? extends Annotation> annotationType, Map<String, Object> parameters) {
        this.parameters = parameters;
        this.annotationType = annotationType;
    }

    @SuppressWarnings({"unchecked"})
    public static <T extends Annotation> T createAnnotation(Class<T> annotationType, Map<String, Object> parameters) {
        return (T) Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{annotationType},
                                          new AnnotationProxy(annotationType, parameters));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        if (parameters.containsKey(method.getName())) {
            return parameters.get(method.getName());
        }

        if (method.getDeclaringClass() == annotationType) {
            return method.getDefaultValue();
        }

        return method.invoke(this, arguments);
    }
}
