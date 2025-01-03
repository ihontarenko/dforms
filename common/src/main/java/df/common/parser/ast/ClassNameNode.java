package df.common.parser.ast;

import df.common.ast.token.Token;

public class ClassNameNode extends LiteralNode {

    private String className;

    public ClassNameNode(Token.Entry entry) {
        super(entry);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}