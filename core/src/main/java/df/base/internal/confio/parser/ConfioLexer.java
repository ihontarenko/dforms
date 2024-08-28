package df.base.internal.confio.parser;

import df.base.internal.parser.lexer.AbstractLexer;
import df.base.internal.parser.token.Token;

import java.util.List;

public class ConfioLexer extends AbstractLexer {

    public ConfioLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
