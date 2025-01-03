package df.common.parser.ast;

import df.common.ast.node.EntryNode;
import df.common.ast.node.Node;

public class FunctionNode extends EntryNode {

    private String methodName;
    private Node   arguments;

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

}