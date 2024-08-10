package df.base.common.confio.parser;

import df.base.common.parser.lexer.AbstractLexer;
import df.base.common.parser.token.Token;

import java.util.List;

public class ConfioLexer extends AbstractLexer {

    public ConfioLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
