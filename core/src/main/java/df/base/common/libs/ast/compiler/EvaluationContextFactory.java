package df.base.common.libs.ast.compiler;

import df.base.common.reflection.Reflections;

import java.util.Arrays;

public class EvaluationContextFactory {

    public static EvaluationContext newEvaluationContext() {
        return new EvaluationContext();
    }

    public static EvaluationContext defaultEvaluationContext(Class<?>... types) {
        EvaluationContext context = newEvaluationContext();

        Arrays.stream(types).flatMap(clazz -> Reflections.extractStaticMethods(clazz).stream())
                .forEach(context::setFunction);

        return context;
    }

}
