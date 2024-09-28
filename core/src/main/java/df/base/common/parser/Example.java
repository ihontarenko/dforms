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

        evaluationContext.setVariable("stringVar", "olololo");

        new DefaultTokenizerConfigurator().configure(tokenizer);
        new DefaultParserConfigurator().configure(context);

        String test = "test string";

        Lexer lexer = new DefaultLexer(tokenizer.tokenize(
                "(result=#service.getValue(123, '456'), staticValue=#random(), complex=#service.hello(#random(), #stringVar.toUpperCase(), 12.34), provider=df.base.common.support.spel.SpelEvaluator, minLength=10 + 2, maxLength=32, array={1, 2, 3})"));
//        Lexer lexer = new DefaultLexer(tokenizer.tokenize("#service.methodName(123, '456')"));
        Node   root   = new RootNode();
        Parser parser = context.getParser(ParametersParser.class);

        parser.shift(lexer, DefaultToken.T_OPEN_BRACE);

        parser.parse(lexer, root, context);

        parser.shift(lexer, DefaultToken.T_CLOSE_BRACE);

        evaluationContext.setVariable("service", new TestService());

        Map<String, Object> params = (Map<String, Object>) root.first().evaluate(evaluationContext);

        System.out.println(params);
    }

}
