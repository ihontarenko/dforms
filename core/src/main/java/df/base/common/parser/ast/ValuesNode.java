package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ValuesNode extends EntryNode {

    private final List<Node> elements = new ArrayList<>();

    public List<Node> getElements() {
        return elements;
    }

    public void addElement(Node element) {
        this.elements.add(element);
    }

    @Override
    public Object evaluate(EvaluationContext ctx) {
        return elements.stream().map(node -> node.evaluate(ctx)).toList();
    }

}