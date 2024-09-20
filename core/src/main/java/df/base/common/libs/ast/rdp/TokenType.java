package df.base.common.libs.ast.rdp;

import df.base.common.libs.ast.token.Token;

public enum TokenType implements Token {

    IDENTIFIER(1000),
    CONDITION(1100),
    METHOD_CALL(1200),
    SYMBOL(9000),
    EOF(9999);

    private final int      type;
    private final String[] values;

    TokenType(final int type) {
        this(type, new String[0]);
    }

    TokenType(final int type, final String... values) {
        this.type = type;
        this.values = values;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public String[] examples() {
        return values;
    }

    @Override
    public TokenType[] tokens() {
        return values();
    }

}
