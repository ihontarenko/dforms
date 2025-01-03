package df.common.parser.parser;

import df.common.parser.ast.ClassNameNode;
import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.parser.ExtendedToken;

import static df.common.ast.token.Token.Entry;

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
