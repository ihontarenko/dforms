package df.base.common.libs.ast.rdp;

import df.base.common.libs.ast.rdp.context.TokenizerContext;
import df.base.common.libs.ast.token.Token;

public interface TokenizerStrategy {

    boolean matches(String input, int position, TokenizerContext ctx);

    Token.Entry tokenize(String input, int position, TokenizerContext ctx);

}
