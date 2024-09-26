package df.base.common.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateInterceptors implements MethodInterceptor {

    private final Predicate<Class<?>>     predicate;
    private final List<MethodInterceptor> interceptors;

    public PredicateInterceptors(Predicate<Class<?>> predicate) {
        this.predicate = predicate;
        this.interceptors = new ArrayList<>();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {


        return null;
    }

}
