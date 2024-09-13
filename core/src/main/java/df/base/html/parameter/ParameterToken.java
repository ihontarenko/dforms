package df.base.html.parameter;

import df.base.common.libs.ast.token.Token;

public enum ParameterToken implements Token {

    T_CONFIG_PARAMETER_SCOPE(9100);

    private final int      type;
    private final String[] values;

    ParameterToken(final int type) {
        this(type, new String[0]);
    }

    ParameterToken(final int type, final String... values) {
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
    public ParameterToken[] tokens() {
        return values();
    }

}
