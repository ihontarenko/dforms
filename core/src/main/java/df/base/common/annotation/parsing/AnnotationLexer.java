package df.base.common.annotation.parsing;

import df.base.common.libs.ast.lexer.AbstractLexer;
import df.base.common.libs.ast.token.Token;

import java.util.List;

public class AnnotationLexer extends AbstractLexer {

    public AnnotationLexer(List<Token.Entry> entries) {
        super(entries);
    }

}
