package df.base.html.parameter;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.html.parameter.ast.ParameterNode;
import df.base.html.parameter.configurator.ParameterParserConfigurator;
import df.base.html.parameter.configurator.ParameterTokenizerConfigurator;
import df.base.html.parameter.parser.ScopeNameParser;

import static df.base.html.parameter.ParameterToken.T_CONFIG_PARAMETER_SCOPE;

public class Demo {

    public static void main(String[] args) {
        ParserContext context   = ParserContext.CONTEXT;
        Tokenizer     tokenizer = new ParameterTokenizer();

        new ParameterTokenizerConfigurator().configure(tokenizer);
        new ParameterParserConfigurator().configure(context);

        Parser parser = context.getParser(ScopeNameParser.class);
        Lexer  lexer  = new ParameterLexer(tokenizer.tokenize("@Run(processor)"));

        // make sure we start parsing with the correct token
        parser.ensureCurrent(lexer, T_CONFIG_PARAMETER_SCOPE);

        Node  root  = new ParameterNode(lexer.current());

        parser.parse(lexer, root, context);

        System.out.println(
                root
        );
    }

}
