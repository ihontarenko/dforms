package df.parent.library.parser.token;

import df.parent.library.parser.Regexps;
import df.parent.library.parser.configurer.Configurator;
import df.parent.library.parser.recognizer.EnumTokenRecognizer;
import df.parent.library.parser.recognizer.JavaTypeTokenRecognizer;

public class TokenizerConfigurator implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        // recognizers
        tokenizer.addRecognizer(new JavaTypeTokenRecognizer());
        tokenizer.addRecognizer(new EnumTokenRecognizer<>(DefaultToken.values(), 2000));
        // expressions
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_QUOTED_STRING_1.expression(), 100));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_FLOAT_1.expression(), 150));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_S_INT.expression(), 200));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_IDENTIFIER.expression(), 300));
        tokenizer.addPattern(new TokenizerPattern(Regexps.R_SPECIAL_SYMBOLS.expression(), 1000));
    }

}
