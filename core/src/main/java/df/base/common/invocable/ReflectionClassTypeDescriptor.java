package df.base.common.invocable;

import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.FieldMatchers;
import df.base.common.reflection.FieldFinder;
import df.base.common.reflection.Finder;
import df.base.common.reflection.MethodFinder;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static df.base.common.libs.jbm.StringUtils.underscored;
import static df.base.common.matcher.reflection.MethodMatchers.*;

public class ReflectionClassTypeDescriptor implements ClassTypeDescriptor {

    private final static Finder<Method> METHOD_FINDER = new MethodFinder();
    private final        Class<?>       nativeClass;

    public ReflectionClassTypeDescriptor(Class<?> nativeClass) {
        this.nativeClass = nativeClass;
    }

    public ReflectionClassTypeDescriptor(Object object) {
        this(Objects.requireNonNull(object).getClass());
    }

    @Override
    public MethodDescriptor getMethod(String name, Class<?>... parameterTypes) {
        Matcher<Executable> matcher = nameEquals(name);

        if (parameterTypes != null && parameterTypes.length != 0) {
            matcher = matcher.and(hasParameterCount(parameterTypes.length));
            matcher = matcher.and(hasParameterTypes(parameterTypes).or(hasSoftParameterTypes(parameterTypes)));
        }

        List<Method> methods = METHOD_FINDER.find(getNativeClass(), matcher);

        if (methods.size() > 1) {
            throw new AmbiguousDescriptorException(
                    "Found more than one method for the given name. Try specifying argument types for disambiguation.");
        }

        if (methods.size() == 0) {
            throw new NoSuchMethodDescriptorException(
                    "No such method exists %s#%s(%s). Please check the method name and try again."
                            .formatted(nativeClass.getCanonicalName(), name, Arrays.toString(parameterTypes)));
        }

        return new ReflectionMethodDescriptor(methods.get(0), nativeClass);
    }

    @Override
    public FieldDescriptor getField(String name) {
        Matcher<Field> matcher = FieldMatchers.nameEquals(name);
        Finder<Field>  finder  = new FieldFinder();

        Optional<Field> optional = finder.findFirst(getNativeClass(), matcher);
        Field           field    = optional.orElseThrow(() -> new NoSuchFieldDescriptorException(
                "No such field exists '%s' in class '%s'. Please check the method name and try again."
                        .formatted(name, nativeClass.getCanonicalName())));

        return new ReflectionFieldDescriptor(field, getNativeClass());
    }

    @Override
    public Class<?> getNativeClass() {
        return nativeClass;
    }

    @Override
    public String getName() {
        return getNativeClass().getName();
    }

    @Override
    public String toString() {
        return "%s: [%s]".formatted(underscored(getClass().getSimpleName()).toUpperCase(), getNativeClass());
    }

}
