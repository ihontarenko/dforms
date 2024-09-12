package df.base.common.libs.ast;

import java.util.function.Predicate;

@SuppressWarnings({"unused"})
public enum Regexps {

    R_QUOTED_STRING_1("\"[^\"]*\""),
    R_QUOTED_STRING_2("'[^']*'"),
    R_LOGICAL_OPERATOR_1("&{2}|\\|{2}"),
    R_LOGICAL_OPERATOR_2("[><!=]+=?"),
    R_NUMBER("[.]?\\d+[.]?\\d*"),
    R_U_INT("\\d+"),
    R_S_INT("[+-]?\\d+"),
    R_FLOAT_1("\\d*\\.\\d*"),
    R_FLOAT_2("[+-]?\\d+(\\.\\d+)?([Ee][+-]?\\d+)?"),
    R_IDENTIFIER("\\w+"),
    R_ALPHA("[a-zA-Z]+"),
    R_SEMICOLON_COMMENT(";[^\n]+|\\([\\w\\s]+\\)"),
    R_NEW_LINE("\n+"),
    R_SPECIAL_SYMBOLS("\\S+?");

    private final String expression;

    Regexps(String expression) {
        this.expression = expression;
    }

    public Predicate<String> predicate() {
        return input -> input.matches(this.expression);
    }

    public String expression() {
        return this.expression;
    }

    @Override
    public String toString() {
        return expression;
    }

}
