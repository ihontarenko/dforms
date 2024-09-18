package df.base.common.annotation.parsing.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.token.Token;

import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationNode extends EntryNode {

    public AnnotationNode(Token.Entry entry) {
        super(entry);
    }

    public String getAnnotationName() {
        return entry.value().substring(1);
    }

    public Map<String, AnnotationParameterNode> getParameters() {

//        children.stream().collect(Collectors.toMap());

        return null;
    }

}
