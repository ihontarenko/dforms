package df.parent.library.confio.parser;

import df.parent.library.parser.configurer.Configurator;
import df.parent.library.parser.recognizer.PatternTokenRecognizer;
import df.parent.library.parser.token.Tokenizer;
import df.parent.library.parser.token.TokenizerConfigurator;
import df.parent.library.parser.token.TokenizerPattern;

import static df.parent.library.confio.parser.ConfioToken.T_CONFIO_FILE_NAME;

public class ConfioTokenizerSetup implements Configurator<Tokenizer> {

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);
        tokenizer.addPattern(new TokenizerPattern("\\w+\\.confio", 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer("\\w+\\.confio", T_CONFIO_FILE_NAME, 100));
    }

}
