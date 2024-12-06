package df.base.common.parser.evaluation;

import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.parser.ast.StringDefinitionNode;
import df.base.common.parser.ast.VariableNode;

import java.util.AbstractMap;

public class StringDefinitionCompiler implements Compiler<StringDefinitionNode> {

    @Override
    public Object compile(StringDefinitionNode node, EvaluationContext ctx) {
        VariableNode variable = (VariableNode) node.getHandler();
        Node         command  = node.getCommand();

        return new AbstractMap.SimpleImmutableEntry<>(variable.getVariableName(), command.evaluate(ctx));
    }

    @Override
    public Class<? extends StringDefinitionNode> nodeType() {
        return StringDefinitionNode.class;
    }

}
