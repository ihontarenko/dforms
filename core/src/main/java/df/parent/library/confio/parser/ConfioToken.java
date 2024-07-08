package df.parent.library.confio.parser;

import df.parent.library.parser.token.Token;

public enum ConfioToken implements Token {

    T_CONFIO_FILE_NAME(9100);

    private final int      type;
    private final String[] values;

    ConfioToken(final int type) {
        this(type, new String[0]);
    }

    ConfioToken(final int type, final String... values) {
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
    public ConfioToken[] tokens() {
        return values();
    }

}
