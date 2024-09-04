package df.base.common.libs.confio.parser;

import df.base.common.libs.parser.lexer.AbstractLexer;
import df.base.common.libs.parser.token.Token;

import java.util.List;

public class ConfioLexer extends AbstractLexer {

    public ConfioLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
