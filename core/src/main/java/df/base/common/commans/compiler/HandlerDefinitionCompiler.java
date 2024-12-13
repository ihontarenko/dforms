package df.base.common.commans.compiler;

import df.base.common.commans.CommandRoute;
import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.StringDefinitionNode;
import df.base.common.parser.ast.VariableNode;

/**
 * {@code HandlerDefinitionCompiler} is responsible for compiling {@link StringDefinitionNode} instances
 * into {@link CommandRoute} objects.
 *
 * <p>This compiler interprets a {@link StringDefinitionNode} to extract a handler variable and command,
 * evaluates them within the given {@link EvaluationContext}, and produces a {@link CommandRoute}
 * representing the handler-command mapping.</p>
 */
public class HandlerDefinitionCompiler implements Compiler<StringDefinitionNode> {

    /**
     * Compiles a {@link StringDefinitionNode} into a {@link CommandRoute}.
     *
     * <p>The method extracts the handler as a {@link VariableNode} and the command from the
     * {@link StringDefinitionNode}. It evaluates these elements in the provided {@link EvaluationContext}
     * to construct a {@link CommandRoute}.</p>
     *
     * @param node the {@link StringDefinitionNode} to compile
     * @param ctx  the {@link EvaluationContext} used for evaluation
     * @return a {@link CommandRoute} representing the compiled handler-command relationship
     */
    @Override
    public Object compile(StringDefinitionNode node, EvaluationContext ctx) {
        VariableNode variable = (VariableNode) node.getHandler();
        String command = (String) node.getCommand().evaluate(ctx);

        return new CommandRoute(variable.getVariableName(), command);
    }

    /**
     * Returns the type of node this compiler handles.
     *
     * @return the {@link Class} representing {@link StringDefinitionNode}
     */
    @Override
    public Class<? extends StringDefinitionNode> nodeType() {
        return StringDefinitionNode.class;
    }
}
