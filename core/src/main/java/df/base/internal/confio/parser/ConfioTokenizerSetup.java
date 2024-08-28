package df.base.internal.confio.parser;

import df.base.internal.parser.configurer.Configurator;
import df.base.internal.parser.recognizer.PatternTokenRecognizer;
import df.base.internal.parser.token.Tokenizer;
import df.base.internal.parser.token.TokenizerConfigurator;
import df.base.internal.parser.token.TokenizerPattern;

import static df.base.internal.confio.parser.ConfioToken.T_CONFIO_FILE_NAME;

public class ConfioTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\w+\\.confio", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\w+\\.confio", T_CONFIO_FILE_NAME, 100));
    }

}
