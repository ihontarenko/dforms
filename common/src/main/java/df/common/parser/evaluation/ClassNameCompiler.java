package df.common.parser.evaluation;

import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.parser.ast.ClassNameNode;
import df.common.reflection.Reflections;

public class ClassNameCompiler implements Compiler<ClassNameNode, Class<?>> {

    @Override
    public Class<?> compile(ClassNameNode node, EvaluationContext evaluationContext) {
        return Reflections.getClassFor(node.getClassName());
    }

    @Override
    public Class<? extends ClassNameNode> nodeType() {
        return ClassNameNode.class;
    }

}
