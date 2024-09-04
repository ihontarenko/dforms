package df.base.common.libs.parser.recognizer;

import df.base.common.libs.parser.Regexps;
import df.base.common.libs.parser.token.DefaultToken;

public class JavaTypeTokenRecognizer extends TokenRecognizerDecorator {

    private static final int PRIORITY = 2500;

    public JavaTypeTokenRecognizer() {
        addRecognizer(new PatternTokenRecognizer(Regexps.R_QUOTED_STRING_1.expression(), DefaultToken.T_STRING, 100));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_QUOTED_STRING_2.expression(), DefaultToken.T_STRING, 150));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_FLOAT_1.expression(), DefaultToken.T_FLOAT, 500));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_S_INT.expression(), DefaultToken.T_INT, 600));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_IDENTIFIER.expression(), DefaultToken.T_IDENTIFIER, 700));
    }

}
