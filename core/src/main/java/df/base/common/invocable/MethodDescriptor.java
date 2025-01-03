package df.base.common.invocable;

import df.base.common.reflection.Reflections;

import java.lang.reflect.Method;

public interface MethodDescriptor extends TypeDescriptor {

    ClassTypeDescriptor getClassTypeDescriptor();

    Method getNativeMethod();

    int getParametersCount();

    Class<?>[] getParameterTypes();

    default String getDetailedName() {
        return Reflections.getMethodName(getNativeMethod());
    }

}
