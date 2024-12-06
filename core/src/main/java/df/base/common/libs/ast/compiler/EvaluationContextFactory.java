package df.base.common.libs.ast.compiler;

import df.base.common.parser.support.BooleanFunctions;
import df.base.common.parser.support.MathFunctions;
import df.base.common.reflection.Reflections;

import java.util.Arrays;
import java.util.stream.Stream;

public class EvaluationContextFactory {

    public static final Class<?>[] DEFAULT_FUNCTIONS= new Class[]{
            MathFunctions.class, BooleanFunctions.class
    };

    public static EvaluationContext newEvaluationContext() {
        return new EvaluationContext();
    }

    public static EvaluationContext defaultEvaluationContext(Class<?>... types) {
        EvaluationContext context = newEvaluationContext();

        Stream.concat(Arrays.stream(types), Arrays.stream(DEFAULT_FUNCTIONS))
                .flatMap(clazz -> Reflections.extractStaticMethods(clazz).stream())
                .forEach(context::setFunction);

        return context;
    }

}
