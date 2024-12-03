package df.base.common.parser;

import df.base.common.libs.ast.compiler.CompilerContext;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.RootNode;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.libs.jbm.ReflectionUtils;
import df.base.common.parser.configurator.CompilerConfigurator;
import df.base.common.parser.configurator.ParserConfigurator;
import df.base.common.parser.configurator.TokenizerConfigurator;
import df.base.common.parser.parser.AnyExpressionParser;

import java.lang.reflect.Method;
import java.util.Map;

public class Example {

    public static void main(String[] args) {
        ParserContext   parserContext = ParserContext.CONTEXT;
        CompilerContext compilerContext = new CompilerContext();
        Tokenizer       tokenizer     = new DefaultTokenizer();
        EvaluationContext evaluationContext = new EvaluationContext();

        for (Method method : ReflectionUtils.extractStaticMethods(TestService.class)) {
            evaluationContext.setFunction(method);
        }

        evaluationContext.setVariable("user", "Ivan");

        new TokenizerConfigurator().configure(tokenizer);
        new ParserConfigurator().configure(parserContext);
        new CompilerConfigurator().configure(compilerContext);

        Lexer lexer = new DefaultLexer(tokenizer.tokenize(
                "(map={1, #user, (key1=123, key2='Hello!', key3={1, 2.3, 3.123123123123, #math.sum(456, 9881)}, key4=#user)})"));

        Node   root   = new RootNode();
        Parser parser = parserContext.getParser(AnyExpressionParser.class);

        parser.parse(lexer, root, parserContext);

        evaluationContext.setVariable("service", new TestService());
        evaluationContext.setVariable("math", new TestService());

        Map<String, Object> params = (Map<String, Object>) root.first().evaluate(evaluationContext);

        System.out.println(params);
    }

}
