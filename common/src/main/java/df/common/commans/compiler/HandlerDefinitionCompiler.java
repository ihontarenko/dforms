package df.common.commans.compiler;

import svit.ast.compiler.Compiler;
import svit.ast.compiler.EvaluationContext;
import df.common.commans.CommandRoute;
import svit.expression.ast.StringDefinitionNode;
import svit.expression.ast.VariableNode;

/**
 * {@code HandlerDefinitionCompiler} is responsible for compiling {@link StringDefinitionNode} instances
 * into {@link CommandRoute} objects.
 *
 * <p>This compiler interprets a {@link StringDefinitionNode} to extract a handler variable and command,
 * evaluates them within the given {@link EvaluationContext}, and produces a {@link CommandRoute}
 * representing the handler-command mapping.</p>
 */
public class HandlerDefinitionCompiler implements Compiler<StringDefinitionNode, CommandRoute> {

    /**
     * Compiles a {@link StringDefinitionNode} into a {@link CommandRoute}.
     *
     * <p>The method extracts the handler as a {@link VariableNode} and the command from the
     * {@link StringDefinitionNode}. It evaluates these elements in the provided {@link EvaluationContext}
     * to construct a {@link CommandRoute}.</p>
     *
     * @param node the {@link StringDefinitionNode} to compile
     * @param evaluationContext  the {@link EvaluationContext} used for evaluation
     * @return a {@link CommandRoute} representing the compiled handler-command relationship
     */
    @Override
    public CommandRoute compile(StringDefinitionNode node, EvaluationContext evaluationContext) {
        VariableNode variable = (VariableNode) node.getHandler();
        String       command  = (String) node.getCommand().evaluate(evaluationContext);

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
