package df.base.common.parser.ast;

import df.base.common.ast.node.EntryNode;

public class VariableNode extends EntryNode {

    private String rawName;

    public String getRawName() {
        return rawName;
    }

    public String getVariableName() {
        return rawName.substring(1);
    }

    public VariableNode(String rawName) {
        this.rawName = rawName;
    }

}
