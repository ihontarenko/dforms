package df.base.common.libs.sel.parser;

import df.base.common.libs.parser.token.Tokenizer;
import df.base.common.libs.parser.token.TokenizerConfigurator;
import df.base.common.libs.parser.configurer.Configurator;
import df.base.common.libs.parser.recognizer.PatternTokenRecognizer;
import df.base.common.libs.parser.token.TokenizerPattern;

public class SELTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\#\\w+", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\#\\w+", SELToken.T_SEL_VARIABLE, 100));
    }

}
