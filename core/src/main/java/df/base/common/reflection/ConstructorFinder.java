package df.base.common.reflection;

import java.lang.reflect.Constructor;

public class ConstructorFinder extends AbstractFinder<Constructor<?>> {

    @Override
    protected Constructor<?>[] getMembers(Class<?> clazz) {
        return clazz.getDeclaredConstructors();
    }

}
