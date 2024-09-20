package df.base.common.libs.ast.recognizer;

import df.base.common.libs.ast.Regexps;
import df.base.common.libs.ast.token.DefaultToken;

public class JavaTypeTokenRecognizer extends TokenRecognizerDecorator {

    private static final int PRIORITY = 2500;

    public JavaTypeTokenRecognizer() {
        addRecognizer(new PatternTokenRecognizer(Regexps.R_QUOTED_STRING_1.regularExpression(), DefaultToken.T_STRING, 100));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_QUOTED_STRING_2.regularExpression(), DefaultToken.T_STRING, 150));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_FLOAT_1.regularExpression(), DefaultToken.T_FLOAT, 500));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_S_INT.regularExpression(), DefaultToken.T_INT, 600));
        addRecognizer(new PatternTokenRecognizer(Regexps.R_IDENTIFIER.regularExpression(), DefaultToken.T_IDENTIFIER, 700));
    }

}
