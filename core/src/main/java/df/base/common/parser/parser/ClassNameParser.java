package df.base.common.parser.parser;

import df.base.common.parser.ast.ClassNameNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.parser.ExtendedToken;

import static df.base.common.libs.ast.token.Token.Entry;

public class ClassNameParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ensureCurrent(lexer, ExtendedToken.T_CLASS_NAME);

        Entry         entry = lexer.current();
        ClassNameNode node  = new ClassNameNode(entry);

        node.setClassName(entry.value());

        parent.add(node);
    }

}
