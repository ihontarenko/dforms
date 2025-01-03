package df.common.parser.ast;

import df.common.ast.node.EntryNode;
import df.common.ast.node.Node;

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

}