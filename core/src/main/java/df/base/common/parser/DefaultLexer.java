package df.base.common.parser;

import df.base.common.ast.lexer.AbstractLexer;
import df.base.common.ast.token.Token;

import java.util.List;

public class DefaultLexer extends AbstractLexer {

    public DefaultLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
