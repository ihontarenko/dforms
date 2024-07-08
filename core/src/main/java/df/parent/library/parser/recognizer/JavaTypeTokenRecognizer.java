package df.parent.library.parser.recognizer;

import static df.parent.library.parser.Regexps.*;
import static df.parent.library.parser.token.DefaultToken.*;

public class JavaTypeTokenRecognizer extends TokenRecognizerDecorator {

    private static final int PRIORITY = 2500;

    public JavaTypeTokenRecognizer() {
        addRecognizer(new PatternTokenRecognizer(R_QUOTED_STRING_1.expression(), T_STRING, 100));
        addRecognizer(new PatternTokenRecognizer(R_QUOTED_STRING_2.expression(), T_STRING, 150));
        addRecognizer(new PatternTokenRecognizer(R_FLOAT_1.expression(), T_FLOAT, 500));
        addRecognizer(new PatternTokenRecognizer(R_S_INT.expression(), T_INT, 600));
        addRecognizer(new PatternTokenRecognizer(R_IDENTIFIER.expression(), T_IDENTIFIER, 700));
    }

}
