package df.base.common.annotation.parsing;

import df.base.common.libs.ast.token.Token;

public enum AnnotationToken implements Token {

    T_ANNOTATION(9100),
    T_ANNOTATION_CLASS_NAME(9200);

    private final int      type;
    private final String[] values;

    AnnotationToken(final int type) {
        this(type, new String[0]);
    }

    AnnotationToken(final int type, final String... values) {
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
    public AnnotationToken[] tokens() {
        return values();
    }

}
