package df.base.common.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.RootNode;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.libs.jbm.ReflectionUtils;
import df.base.common.parser.configurator.DefaultParserConfigurator;
import df.base.common.parser.configurator.DefaultTokenizerConfigurator;
import df.base.common.parser.parser.AnyExpressionParser;
import df.base.common.parser.parser.ParametersParser;

import java.lang.reflect.Method;
import java.util.Map;

public class Example {

    public static void main(String[] args) {
        ParserContext     context           = ParserContext.CONTEXT;
        Tokenizer         tokenizer         = new DefaultTokenizer();
        EvaluationContext evaluationContext = new EvaluationContext();

        for (Method method : ReflectionUtils.extractStaticMethods(TestService.class)) {
            evaluationContext.setFunction(method);
        }

        evaluationContext.setVariable("user", "Ivan");

        new DefaultTokenizerConfigurator().configure(tokenizer);
        new DefaultParserConfigurator().configure(context);

        Lexer lexer = new DefaultLexer(tokenizer.tokenize(
                "(map={1, #user, (key1=123, key2='Hello!', key3={1, 2, 3, #service.getValue(1, 'test')}, key4=#user)})"));

        Node   root   = new RootNode();
        Parser parser = context.getParser(AnyExpressionParser.class);

        parser.parse(lexer, root, context);

        evaluationContext.setVariable("service", new TestService());

        Map<String, Object> params = (Map<String, Object>) root.first().evaluate(evaluationContext);

        System.out.println(params);
    }

}
