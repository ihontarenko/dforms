package df.base.html.builder.parameters;

import df.base.common.libs.ast.configurer.Configurator;
import df.base.common.libs.ast.recognizer.PatternTokenRecognizer;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.libs.ast.token.TokenizerConfigurator;
import df.base.common.libs.ast.token.TokenizerPattern;

import static df.base.html.builder.parameters.ParameterToken.T_CONFIG_PARAMETER_SCOPE;

public class TokenizerSetup implements Configurator<Tokenizer> {

    public static final String SCOPE_REGEX = "\\@\\w+";

    @Override
    public void configure(Tokenizer tokenizer) {
        new TokenizerConfigurator().configure(tokenizer);

        tokenizer.addPattern(new TokenizerPattern(SCOPE_REGEX, 100));
        tokenizer.addRecognizer(new PatternTokenRecognizer(SCOPE_REGEX, T_CONFIG_PARAMETER_SCOPE, 100));
    }

}
