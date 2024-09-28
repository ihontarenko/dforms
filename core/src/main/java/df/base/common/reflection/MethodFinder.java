package df.base.common.reflection;

import java.lang.reflect.Method;

public class MethodFinder extends AbstractFinder<Method> {

    @Override
    protected Method[] getMembers(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

}
