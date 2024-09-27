package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.ast.Literal;
import df.base.common.libs.ast.token.Token;

import static df.base.common.libs.jbm.ReflectionUtils.getClassFor;

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

    @Override
    public Object evaluate(EvaluationContext ctx) {
        return getClassFor(className);
    }

}
