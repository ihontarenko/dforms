package df.base.common.proxy;

import java.lang.reflect.Method;

public interface MethodInvocation {

    Object proceed() throws Throwable;

    Method getMethod();

    Object[] getArguments();

    Object getTarget();

    @FunctionalInterface
    interface Executable {
        Object execute(Object target, Method method, Object[] arguments) throws Throwable;
    }

    record DefaultMethodInvocation(Object target, Method method, Object[] arguments,
                                   Executable executable)
            implements MethodInvocation {
        public Object proceed() throws Throwable {
            return executable.execute(target, method, arguments);
        }

        @Override
        public Method getMethod() {
            return null;
        }

        @Override
        public Object[] getArguments() {
            return new Object[0];
        }

        @Override
        public Object getTarget() {
            return null;
        }
    }

}
