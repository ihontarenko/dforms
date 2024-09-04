package df.base.common.sel.parser;

import df.base.common.parser.token.Token;

public enum SELToken implements Token {

    T_SEL_VARIABLE(9100);

    private final int      type;
    private final String[] values;

    SELToken(final int type) {
        this(type, new String[0]);
    }

    SELToken(final int type, final String... values) {
        this.type = type;
        this.values = values;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public String[] expressions() {
        return values;
    }

    @Override
    public SELToken[] tokens() {
        return values();
    }

}
