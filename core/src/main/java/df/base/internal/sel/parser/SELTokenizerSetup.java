package df.base.internal.sel.parser;

import df.base.internal.parser.configurer.Configurator;
import df.base.internal.parser.recognizer.PatternTokenRecognizer;
import df.base.internal.parser.token.Tokenizer;
import df.base.internal.parser.token.TokenizerConfigurator;
import df.base.internal.parser.token.TokenizerPattern;

public class SELTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\#\\w+", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\#\\w+", SELToken.T_SEL_VARIABLE, 100));
    }

}
