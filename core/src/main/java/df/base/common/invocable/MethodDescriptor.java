package df.base.common.invocable;

import java.lang.reflect.Method;

public interface MethodDescriptor extends TypeDescriptor {

    ClassTypeDescriptor getClassTypeDescriptor();

    Method getNativeMethod();

    int getParametersCount();

    Class<?>[] getParameterTypes();

}
