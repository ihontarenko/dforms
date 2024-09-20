package df.base.common.annotation.parsing.parser;

import df.base.common.annotation.parsing.ast.AnnotationClassNameNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;

import static df.base.common.annotation.parsing.AnnotationToken.T_ANNOTATION_CLASS_NAME;
import static df.base.common.libs.ast.token.DefaultToken.Entry;

public class AnnotationClassNameParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ensureCurrent(lexer, T_ANNOTATION_CLASS_NAME);

        Entry                   entry = lexer.current();
        AnnotationClassNameNode node  = new AnnotationClassNameNode(entry);

        node.setClassName(entry.value());

        parent.add(node);
    }

}
