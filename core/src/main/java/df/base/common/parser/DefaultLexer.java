package df.base.common.parser;

import df.base.common.libs.ast.lexer.AbstractLexer;
import df.base.common.libs.ast.token.Token;

import java.util.List;

public class DefaultLexer extends AbstractLexer {

    public DefaultLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
