package df.base.common.annotation.parsing.configurator;

import df.base.common.libs.ast.configurer.Configurator;
import df.base.common.libs.ast.recognizer.PatternTokenRecognizer;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.libs.ast.token.TokenizerConfigurator;
import df.base.common.libs.ast.token.TokenizerPattern;
import df.base.common.annotation.parsing.AnnotationToken;

public class AnnotationTokenizerConfigurator implements Configurator<Tokenizer> {

    public static final String ANNOTATION = "\\@\\w+";
    public static final String CLASS_NAME = "(\\w+(?:\\.\\w+)+)";

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);

        tokenizer.addPattern(new TokenizerPattern(ANNOTATION, 100));
        tokenizer.addPattern(new TokenizerPattern(CLASS_NAME, 200));
        tokenizer.addRecognizer(new PatternTokenRecognizer(ANNOTATION, AnnotationToken.T_ANNOTATION, 7000));
        tokenizer.addRecognizer(new PatternTokenRecognizer(CLASS_NAME, AnnotationToken.T_ANNOTATION_CLASS_NAME, 7100));
    }

}
