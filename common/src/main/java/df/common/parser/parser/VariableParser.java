package df.common.parser.parser;

import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.parser.ExtendedToken;
import df.common.parser.ast.VariableNode;

public class VariableParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, ExtendedToken.T_VARIABLE);
        parent.add(new VariableNode(lexer.current().value()));
    }

}
