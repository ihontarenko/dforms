package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.jbm.ReflectionUtils;
import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;
import df.base.common.parser.EvaluationException;
import df.base.common.reflection.Finder;
import df.base.common.reflection.MethodFinder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static df.base.common.matcher.reflection.MethodMatchers.hasSoftParameterTypes;
import static df.base.common.matcher.reflection.MethodMatchers.nameEquals;

public class ObjectMethodNode extends EntryNode {

    private String objectName;
    private String methodName;
    private Node   arguments;

    private final Finder<Method> finder  = new MethodFinder();
    private final MatchContext   context = MatchContext.createDefault();

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Node getArguments() {
        return arguments;
    }

    public void setArguments(Node arguments) {
        this.arguments = arguments;
    }

    @Override
    public Object evaluate(EvaluationContext evaluationContext) {
        Object[]        arguments     = this.arguments == null ? new Object[0] : ((List<?>) this.arguments.evaluate(evaluationContext)).toArray(Object[]::new);
        Class<?>[]      argumentTypes = ReflectionUtils.getArgumentsTypes(arguments);
        Object          object        = evaluationContext.requireVariable(objectName);
        Matcher<Method> matcher       = Matcher.and(nameEquals(methodName), hasSoftParameterTypes(argumentTypes));

        Method method = finder.findFirst(object.getClass(), matcher, context).orElseThrow(() -> new EvaluationException(
                "No such method '%s#%s(%s)'".formatted(object.getClass().getName(), methodName, Arrays.toString(argumentTypes))));

        return ReflectionUtils.invokeMethod(object, method, arguments);
    }

}
