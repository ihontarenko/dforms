package df.base.common.annotation.parsing.ast;

import df.base.common.libs.ast.node.ast.Literal;
import df.base.common.libs.ast.token.Token;

public class AnnotationClassNameNode extends Literal {

    private String className;

    public AnnotationClassNameNode(Token.Entry entry) {
        super(entry);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
