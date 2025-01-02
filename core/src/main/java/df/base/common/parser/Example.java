package df.base.common.parser;

import df.base.common.ast.compiler.EvaluationContextFactory;
import df.base.common.ast.lexer.Lexer;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.ast.node.Node;
import df.base.common.ast.node.RootNode;
import df.base.common.ast.parser.Parser;
import df.base.common.ast.parser.ParserContext;
import df.base.common.ast.token.Tokenizer;
import df.base.common.parser.evaluation.EvaluationContextConfigurator;
import df.base.common.parser.parser.ParserConfigurator;
import df.base.common.parser.parser.AnyExpressionParser;
import df.base.common.parser.support.BooleanFunctions;
import df.base.common.parser.support.MathFunctions;
import df.base.common.parser.test.ExampleDto;
import df.base.common.parser.test.TestService;

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

        String el1 = "(values={#validation:non_null, (applier=#lte(#item.getQty(), 100), qty=#item.getQty(), name=#item.getName()), #user.startsWith('Iv'), #lt(1, 2), #multiply(7, #divide(22, 7)), #divide(22, 7), df.base.common.parser.Example, 1, #user, (key1=123, key2='Hello!', key3={1, 2.3, 3.123123123123, #math.sum(456, 9881), df.base.common.parser.Example}, key4=#user)})";
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
