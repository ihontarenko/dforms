package df.base.common.parser.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.ast.ObjectMethodNode;
import df.base.common.libs.ast.node.ast.StaticMethodNode;
import df.base.common.libs.ast.node.ast.VariableNode;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.parser.ExtendedToken;

import static df.base.common.parser.DefaultMatcher.*;

public class ExternalVariableParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        boolean isObjectMethod = OBJECT_METHOD.test(lexer);
        boolean isVariable     = VARIABLE.test(lexer);
        boolean isStaticMethod = STATIC_METHOD.test(lexer);

        if (isObjectMethod) {
            shift(lexer, ExtendedToken.T_VARIABLE);

            ObjectMethodNode node       = new ObjectMethodNode();
            String           objectName = lexer.current().value().substring(1);

            lexer.forward(DefaultToken.T_IDENTIFIER);
            String methodName = lexer.current().value();

            node.setObjectName(objectName);
            node.setMethodName(methodName);

            shift(lexer, DefaultToken.T_OPEN_BRACE);

            if (!lexer.isNext(DefaultToken.T_CLOSE_BRACE)) {
                node.setArguments(context.getParser(CommaSeparatedParser.class).parse(lexer, context));
            }

            shift(lexer, DefaultToken.T_CLOSE_BRACE);

            parent.add(node);
        } else if (isStaticMethod) {
            shift(lexer, ExtendedToken.T_VARIABLE);

            StaticMethodNode node       = new StaticMethodNode();
            String           methodName = lexer.current().value().substring(1);

            node.setMethodName(methodName);

            shift(lexer, DefaultToken.T_OPEN_BRACE);

            if (!lexer.isNext(DefaultToken.T_CLOSE_BRACE)) {
                node.setArguments(context.getParser(CommaSeparatedParser.class).parse(lexer, context));
            }

            shift(lexer, DefaultToken.T_CLOSE_BRACE);

            parent.add(node);
        } else if (isVariable) {
            shift(lexer, ExtendedToken.T_VARIABLE);
            VariableNode variableNode = new VariableNode(lexer.current());

            parent.add(variableNode);
        } else {
            throwSyntaxErrorException(lexer, 0, "either #variable, #staticMethod or #instance.method()");
        }
    }

}
