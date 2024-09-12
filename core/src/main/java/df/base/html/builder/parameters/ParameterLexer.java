package df.base.html.builder.parameters;

import df.base.common.libs.ast.lexer.AbstractLexer;
import df.base.common.libs.ast.token.Token;

import java.util.List;

public class ParameterLexer extends AbstractLexer {

    public ParameterLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
