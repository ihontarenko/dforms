package df.base.common.confio.parser;

import df.base.common.parser.configurer.Configurator;
import df.base.common.parser.recognizer.PatternTokenRecognizer;
import df.base.common.parser.token.Tokenizer;
import df.base.common.parser.token.TokenizerConfigurator;
import df.base.common.parser.token.TokenizerPattern;

import static df.base.common.confio.parser.ConfioToken.T_CONFIO_FILE_NAME;

public class ConfioTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\w+\\.confio", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\w+\\.confio", T_CONFIO_FILE_NAME, 100));
    }

}
