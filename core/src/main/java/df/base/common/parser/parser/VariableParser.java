package df.base.common.parser.parser;

import df.base.common.ast.lexer.Lexer;
import df.base.common.ast.node.Node;
import df.base.common.ast.parser.Parser;
import df.base.common.ast.parser.ParserContext;
import df.base.common.parser.ExtendedToken;
import df.base.common.parser.ast.VariableNode;

public class VariableParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, ExtendedToken.T_VARIABLE);
        parent.add(new VariableNode(lexer.current().value()));
    }

}
