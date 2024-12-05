package df.base.common.parser;

import df.base.common.libs.ast.compiler.EvaluationContextFactory;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.RootNode;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.parser.evaluation.EvaluationContextConfigurator;
import df.base.common.parser.parser.ParserConfigurator;
import df.base.common.parser.parser.AnyExpressionParser;
import df.base.common.parser.support.MathFunctions;

public class Example {

    public static void main(String[] args) {
        ParserContext   parserContext = ParserContext.CONTEXT;
        Tokenizer       tokenizer     = new DefaultTokenizer();
        EvaluationContext evaluationContext = EvaluationContextFactory.defaultEvaluationContext(TestService.class,
                                                                                                MathFunctions.class);

        evaluationContext.setVariable("user", "Ivan");

        new TokenizerConfigurator().configure(tokenizer);
        new ParserConfigurator().configure(parserContext);
        new EvaluationContextConfigurator().configure(evaluationContext);

        Lexer lexer = new DefaultLexer(tokenizer.tokenize(
                "(map={#divide(2.000000000001, 7.000000000001), df.base.common.parser.Example, 1, #user, (key1=123, key2='Hello!', key3={1, 2.3, 3.123123123123, #math.sum(456, 9881), df.base.common.parser.Example}, key4=#user)})"));

        Node   root   = new RootNode();
        Parser parser = parserContext.getParser(AnyExpressionParser.class);

        parser.parse(lexer, root, parserContext);

        evaluationContext.setVariable("service", new TestService());
        evaluationContext.setVariable("math", new TestService());

        Object params = root.evaluate(evaluationContext);

        System.out.println(params);
    }

}
