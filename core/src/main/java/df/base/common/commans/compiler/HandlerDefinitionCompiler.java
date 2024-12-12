package df.base.common.commans.compiler;

import df.base.common.commans.CommandRoute;
import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.StringDefinitionNode;
import df.base.common.parser.ast.VariableNode;

public class HandlerDefinitionCompiler implements Compiler<StringDefinitionNode> {

    @Override
    public Object compile(StringDefinitionNode node, EvaluationContext ctx) {
        VariableNode variable = (VariableNode) node.getHandler();
        String       command  = (String) node.getCommand().evaluate(ctx);

        return new CommandRoute(variable.getVariableName(), command);
    }

    @Override
    public Class<? extends StringDefinitionNode> nodeType() {
        return StringDefinitionNode.class;
    }

}
