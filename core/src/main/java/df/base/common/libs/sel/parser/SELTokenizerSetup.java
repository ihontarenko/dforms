package df.base.common.libs.sel.parser;

import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.libs.ast.token.TokenizerConfigurator;
import df.base.common.libs.ast.configurer.Configurator;
import df.base.common.libs.ast.recognizer.PatternTokenRecognizer;
import df.base.common.libs.ast.token.TokenizerPattern;

public class SELTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\#\\w+", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\#\\w+", SELToken.T_SEL_VARIABLE, 100));
    }

}
