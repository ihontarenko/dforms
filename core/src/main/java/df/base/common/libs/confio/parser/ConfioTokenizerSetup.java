package df.base.common.libs.confio.parser;

import df.base.common.libs.parser.configurer.Configurator;
import df.base.common.libs.parser.recognizer.PatternTokenRecognizer;
import df.base.common.libs.parser.token.Tokenizer;
import df.base.common.libs.parser.token.TokenizerConfigurator;
import df.base.common.libs.parser.token.TokenizerPattern;

import static df.base.common.libs.confio.parser.ConfioToken.T_CONFIO_FILE_NAME;

public class ConfioTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\w+\\.confio", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\w+\\.confio", T_CONFIO_FILE_NAME, 100));
    }

}
