package df.base.common.ast.token;

import df.base.common.ast.Regexps;
import df.base.common.ast.configurer.Configurator;
import df.base.common.ast.recognizer.EnumTokenRecognizer;
import df.base.common.ast.recognizer.JavaTypeTokenRecognizer;

public class TokenizerConfigurator implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        // recognizers
        tokenizer.addRecognizer(new JavaTypeTokenRecognizer());
        tokenizer.addRecognizer(new EnumTokenRecognizer<>(DefaultToken.values(), 2000));
        // expressions
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_QUOTED_STRING_1.regularExpression(), 100));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_QUOTED_STRING_2.regularExpression(), 110));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_FLOAT_1.regularExpression(), 150));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_INT.regularExpression(), 200));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_IDENTIFIER.regularExpression(), 300));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_SPECIAL_SYMBOLS.regularExpression(), 1000));
    }

}
