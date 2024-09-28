package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.jbm.ReflectionUtils;
import df.base.common.libs.jbm.bean.MethodNotFoundException;
import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.MethodMatchers;
import df.base.common.reflection.Finder;
import df.base.common.reflection.MethodFinder;

import java.lang.reflect.Method;
import java.util.List;

import static df.base.common.matcher.reflection.MethodMatchers.hasSoftParameterTypes;
import static df.base.common.matcher.reflection.MethodMatchers.nameEquals;

public class ObjectMethodNode extends EntryNode {

    private String objectName;
    private String methodName;
    private Node   arguments;

    private Finder<Method> finder  = new MethodFinder();
    private MatchContext   context = MatchContext.createDefault();

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
    public Object evaluate(EvaluationContext ctx) {
        Object[]        arguments     = this.arguments == null ? new Object[0] : ((List<?>) this.arguments.evaluate(ctx)).toArray(Object[]::new);
        Class<?>[]      argumentTypes = ReflectionUtils.getArgumentsTypes(arguments);
        Object          object        = ctx.requireVariable(objectName);
        Matcher<Method> matcher       = Matcher.and(nameEquals(methodName), hasSoftParameterTypes(argumentTypes));

        Method method = finder.findFirst(object.getClass(), matcher, context).orElseThrow(()
                -> new MethodNotFoundException("NO SUCH METHOD '%s' FOUND".formatted(methodName)));

        return ReflectionUtils.invokeMethod(object, method, arguments);
    }

}
