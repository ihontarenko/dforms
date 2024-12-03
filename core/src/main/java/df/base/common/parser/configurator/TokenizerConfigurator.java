package df.base.common.parser.configurator;

import df.base.common.libs.ast.configurer.Configurator;
import df.base.common.libs.ast.recognizer.PatternTokenRecognizer;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.libs.ast.token.TokenizerPattern;
import df.base.common.parser.ExtendedToken;

public class TokenizerConfigurator implements Configurator<Tokenizer> {

    public static final String ANNOTATION = "\\@\\w+";
    public static final String CLASS_NAME = "(\\w+(?:\\.\\w+)+)";
    public static final String VARIABLE = "\\#\\w+";

    @Override
    public void configure(Tokenizer tokenizer) {
        new df.base.common.libs.ast.token.TokenizerConfigurator().configure(tokenizer);

        tokenizer.addPattern(new TokenizerPattern(ANNOTATION, 100));
        tokenizer.addPattern(new TokenizerPattern(CLASS_NAME, 200));
        tokenizer.addPattern(new TokenizerPattern(VARIABLE, 300));
        tokenizer.addRecognizer(new PatternTokenRecognizer(ANNOTATION, ExtendedToken.T_ANNOTATION, 7000));
        tokenizer.addRecognizer(new PatternTokenRecognizer(CLASS_NAME, ExtendedToken.T_CLASS_NAME, 7100));
        tokenizer.addRecognizer(new PatternTokenRecognizer(VARIABLE, ExtendedToken.T_VARIABLE, 7100));
    }

}
