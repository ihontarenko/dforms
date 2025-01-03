package df.common.parser;

import df.common.ast.lexer.AbstractLexer;
import df.common.ast.token.Token;

import java.util.List;

public class DefaultLexer extends AbstractLexer {

    public DefaultLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
