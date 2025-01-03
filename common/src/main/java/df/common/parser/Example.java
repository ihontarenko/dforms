package df.common.parser;

import df.common.ast.compiler.EvaluationContextFactory;
import df.common.ast.lexer.Lexer;
import df.common.ast.compiler.EvaluationContext;
import df.common.ast.node.Node;
import df.common.ast.node.RootNode;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.ast.token.Tokenizer;
import df.common.parser.evaluation.EvaluationContextConfigurator;
import df.common.parser.parser.ParserConfigurator;
import df.common.parser.parser.AnyExpressionParser;
import df.common.parser.support.BooleanFunctions;
import df.common.parser.support.MathFunctions;
import df.common.parser.test.ExampleDto;
import df.common.parser.test.TestService;

public class Example {

    public static void main(String[] args) {
        ParserContext   parserContext = ParserContext.CONTEXT;
        Tokenizer       tokenizer     = new DefaultTokenizer();
        EvaluationContext evaluationContext = EvaluationContextFactory.defaultEvaluationContext(
                TestService.class, MathFunctions.class, BooleanFunctions.class);

        evaluationContext.setVariable("user", "Ivan");
        evaluationContext.setVariable("item", new ExampleDto());

        new TokenizerConfigurator().configure(tokenizer);
        new ParserConfigurator().configure(parserContext);
        new EvaluationContextConfigurator().configure(evaluationContext);

        String el1 = "(values={#validation:non_null, (applier=#lte(#item.getQty(), 100), qty=#item.getQty(), name=#item.getName()), #user.startsWith('Iv'), #lt(1, 2), #multiply(7, #divide(22, 7)), #divide(22, 7), df.common.parser.Example, 1, #user, (key1=123, key2='Hello!', key3={1, 2.3, 3.123123123123, #math.sum(456, 9881), df.base.common.parser.Example}, key4=#user)})";
        String el2 = "#validation:non_null";

        Lexer lexer = new DefaultLexer(tokenizer.tokenize(el2));

        Node   root   = new RootNode();
        Parser parser = parserContext.getParser(AnyExpressionParser.class);

        parser.parse(lexer, root, parserContext);

        evaluationContext.setVariable("service", new TestService());
        evaluationContext.setVariable("math", new TestService());

        Object params = root.evaluate(evaluationContext);

        System.out.println(params);
    }

}
