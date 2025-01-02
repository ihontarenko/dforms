package df.base.common.parser.evaluation;

import df.base.common.ast.compiler.Compiler;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.ClassNameNode;
import df.base.common.reflection.Reflections;

public class ClassNameCompiler implements Compiler<ClassNameNode> {

    @Override
    public Object compile(ClassNameNode node, EvaluationContext ctx) {
        return Reflections.getClassFor(node.getClassName());
    }

    @Override
    public Class<? extends ClassNameNode> nodeType() {
        return ClassNameNode.class;
    }

}
