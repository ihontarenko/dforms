package df.base.common.annotation.parsing;

import df.base.common.annotation.parsing.ast.AnnotationParameterNode;
import df.base.common.annotation.parsing.configurator.AnnotationParserConfigurator;
import df.base.common.annotation.parsing.configurator.AnnotationTokenizerConfigurator;
import df.base.common.annotation.parsing.parser.AnnotationParameterParser;
import df.base.common.annotation.parsing.parser.AnnotationParser;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.RootNode;
import df.base.common.libs.ast.node.ast.Literal;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.Tokenizer;

import java.util.HashMap;
import java.util.Map;

public class Example {

    public static void main(String[] args) {
        ParserContext context   = ParserContext.CONTEXT;
        Tokenizer     tokenizer = new AnnotationTokenizer();

        new AnnotationTokenizerConfigurator().configure(tokenizer);
        new AnnotationParserConfigurator().configure(context);

        Parser parser = context.getParser(AnnotationParser.class);
//        Lexer  lexer  = new AnnotationLexer(tokenizer.tokenize("@NotEmpty(message = \"form description is required\", size={@Size(max = 32)}, item=@Size(max = 32))"));
        Lexer lexer = new AnnotationLexer(tokenizer.tokenize("(minLength=10, maxLength=32)"));
        Node  root  = new RootNode();

        context.getParser(AnnotationParameterParser.class).parse(lexer, root, context);

        Map<String, Object> params = new HashMap<>();

        for (Node child : root.children()) {
            if (child instanceof AnnotationParameterNode parameterNode) {
                Object value = null;

                if (parameterNode.getValue() instanceof Literal literal) {
                    value = literal.getValue();
                }

                params.put(parameterNode.getKey(), value);
            }
        }

        System.out.println(params);
    }

}
