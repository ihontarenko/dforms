package df.base.common.sel.parser;

import df.base.common.parser.configurer.Configurator;
import df.base.common.parser.recognizer.PatternTokenRecognizer;
import df.base.common.parser.token.Tokenizer;
import df.base.common.parser.token.TokenizerConfigurator;
import df.base.common.parser.token.TokenizerPattern;

import static df.base.common.sel.parser.SELToken.T_SEL_VARIABLE;

public class SELTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\#\\w+", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\#\\w+", T_SEL_VARIABLE, 100));
    }

}
