package df.base.common.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodFinder {

    public static List<Method> findMethods(Class<?> clazz, Matcher<Class<?>> matcher) {
        List<Method> methods = new ArrayList<>();

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (matcher.matches(clazz, MatchContext.createDefault())) {
                methods.add(declaredMethod);
            }
        }

        return methods;
    }

}
