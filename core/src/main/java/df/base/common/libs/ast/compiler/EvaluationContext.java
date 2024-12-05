package df.base.common.libs.ast.compiler;

import df.base.common.context.AbstractContext;
import df.base.common.libs.ast.node.Node;

import java.lang.reflect.Method;
import java.util.Objects;

public class EvaluationContext extends AbstractContext {

    public Method getFunction(String name) {
        Method function = getProperty(name);

        if (function == null) {
            throw new EvaluationContextException(
                    "Evaluation context does not contain the required function '%s'.".formatted(name));
        }

        return function;
    }

    public void setFunction(String name, Method method) {
        setProperty(name, method);
    }

    public void setFunction(Method method) {
        setProperty(method.getName(), method);
    }

    public Object requireVariable(String name) {
        Object variable = getVariable(name);

        if (variable == null) {
            throw new EvaluationContextException(
                    "Evaluation context does not contain the required variable '%s'.".formatted(name));
        }

        return variable;
    }

    public Object getVariable(String name) {
        return getProperty(name);
    }

    public void setVariable(String name, Object value) {
        setProperty(name, value);
    }

    public void addCompiler(Class<? extends Node> nodeType, Compiler<?> compiler) {
        setProperty(nodeType, compiler);
    }

    public void addCompiler(Compiler<?> compiler) {
        addCompiler(Objects.requireNonNull(
                compiler.nodeType(), "Compiler '%s' must return a valid node type class from the nodeType() method."
                        .formatted(compiler.getClass().getName())), compiler);
    }

    public <N extends Node> Compiler<N> getCompiler(Class<? extends N> nodeType) {
        Compiler<N> compiler = getProperty(nodeType);

        if (compiler == null) {
            throw new EvaluationContextException(
                    "Evaluation context does not contain the required compiler for '%s' node."
                            .formatted(nodeType.getName()));
        }

        return compiler;
    }

    public <N extends Node> Compiler<N> getCompiler(N nodeObject) {
        return (Compiler<N>) getCompiler(nodeObject.getClass());
    }

}
