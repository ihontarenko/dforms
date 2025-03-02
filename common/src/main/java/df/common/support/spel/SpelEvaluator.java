package df.common.support.spel;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.jmouse.core.reflection.Reflections;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

public class SpelEvaluator {

    public static final  RuntimeException                    INITIALIZE_EVALUATION_CONTEXT_EXCEPTION
            = new IllegalStateException("INITIALIZE EVALUATION CONTEXT BEFORE CALL UNINITIALIZER");
    public static final  RuntimeException                    UNINITIALIZE_EVALUATION_CONTEXT_EXCEPTION
            = new IllegalStateException("UNINITIALIZE EVALUATION CONTEXT BEFORE CALL INITIALIZER");
    public static final  RuntimeException                    INITIALIZE_EVALUATION_BEFORE_EVALUATION
            = new IllegalStateException("INITIALIZE EVALUATION CONTEXT BEFORE EVALUATION");
    private static final String                              BEAN_FACTORY_IS_REQUIRED
            = "BEAN FACTORY IS REQUIRED";
    private static final TypeConverter                       TYPE_CONVERTER
            = new BooleanTypeConverterDecorator(new StandardTypeConverter());
    private static final Consumer<StandardEvaluationContext> NOOP_CONSUMER
            = ctx -> {};

    private final BeanFactory               factory;
    private final StandardEvaluationContext evaluationContext;
    private       boolean                   initialized = false;

    public SpelEvaluator(BeanFactory factory) {
        this.factory = factory;
        evaluationContext = createEvaluationContext();
    }

    public void initialize(Consumer<StandardEvaluationContext> initializer) {
        if (initialized) {
            throw UNINITIALIZE_EVALUATION_CONTEXT_EXCEPTION;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        evaluationContext.setVariable("principal", authentication.getPrincipal());
        evaluationContext.setVariable("authentication", authentication);
        evaluationContext.setVariable("auth", authentication); // just alias

        // hasRole('USER'), isPrincipal(String userId), etc.
        // registerFunctions(ReflectionUtils.extractStaticMethods(AuthenticationHelper.class));
        // Spring's StringUtils functions
        registerFunctions(Reflections.extractStaticMethods(StringUtils.class));

        requireNonNullElse(initializer, NOOP_CONSUMER).accept(evaluationContext);

        initialized = true;
    }

    public void uninitialize(Consumer<StandardEvaluationContext> uninitializer) {
        if (!initialized) {
            throw INITIALIZE_EVALUATION_CONTEXT_EXCEPTION;
        }

        evaluationContext.setRootObject(null);

        requireNonNullElse(uninitializer, NOOP_CONSUMER).accept(evaluationContext);

        initialized = false;
    }

    public <T> T evaluate(Expression expression, Class<T> type, Consumer<StandardEvaluationContext> initializer) {
        if (!initialized) {
            throw INITIALIZE_EVALUATION_BEFORE_EVALUATION;
        }

        if (initializer != null) {
            initializer.accept(evaluationContext);
        }

        return expression.getValue(evaluationContext, type);
    }

    public <T> T evaluate(Expression expression, Class<T> type) {
        return evaluate(expression, type, null);
    }

    public void registerFunction(Method function) {
        evaluationContext.registerFunction(function.getName(), function);
    }

    public void registerFunctions(Iterable<Method> functions) {
        for (Method method : requireNonNull(functions)) {
            registerFunction(method);
        }
    }

    private StandardEvaluationContext createEvaluationContext() {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

        evaluationContext.setTypeConverter(TYPE_CONVERTER);
        evaluationContext.setBeanResolver(new BeanFactoryResolver(requireNonNull(factory, BEAN_FACTORY_IS_REQUIRED)));

        return evaluationContext;
    }

}
