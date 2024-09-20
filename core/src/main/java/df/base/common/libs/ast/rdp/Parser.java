package df.base.common.libs.ast.rdp;

import df.base.common.libs.ast.rdp.context.ParserContext;
import df.base.common.libs.ast.rdp.node.EvaluableNode;
import df.base.common.libs.ast.rdp.node.IdentifierNode;
import df.base.common.libs.ast.rdp.node.MethodCallNode;
import df.base.common.libs.ast.token.Token;

import java.util.ArrayList;
import java.util.List;

class Parser {

    private final List<Token.Entry> entries;
    private       int               currentPosition;
    private       Token.Entry       currentEntry;

    public Parser(List<Token.Entry> tokens) {
        this.entries = tokens;
        this.currentPosition = 0;
        this.currentEntry = tokens.get(currentPosition);
    }

    public EvaluableNode parse(ParserContext ctx) {
        EvaluableNode expression = parseExpression(ctx);
        if (currentEntry.token() != TokenType.SYMBOL || !currentEntry.value().equals(";")) {
            throw new RuntimeException("expected ';'");
        }
        return expression;
    }

    private EvaluableNode parseExpression(ParserContext ctx) {
        EvaluableNode identifier = parseIdentifier(ctx);

        while (currentEntry.token() == TokenType.SYMBOL && currentEntry.value().equals(".")) {
            consume(ctx);
            if (currentEntry.token() == TokenType.METHOD_CALL) {
                identifier = parseMethodCall(identifier, ctx);
            }
        }

        return identifier;
    }

    private EvaluableNode parseIdentifier(ParserContext ctx) {
        if (currentEntry.token() == TokenType.IDENTIFIER) {
            EvaluableNode node = new IdentifierNode(currentEntry.value());
            consume(ctx);
            return node;
        }
        throw new RuntimeException("Очікується ідентифікатор.");
    }

    private EvaluableNode parseMethodCall(EvaluableNode object, ParserContext ctx) {
        consume(ctx); // Споживаємо токен .call
        List<EvaluableNode> args = new ArrayList<>();

        consume(ctx); // Споживаємо (
        if (currentEntry.token() != TokenType.SYMBOL || !currentEntry.value().equals(")")) {
            args.add(parseExpression(ctx));
        }
        consume(ctx); // Споживаємо )

        return new MethodCallNode(object, "call", args);
    }

    private void consume(ParserContext ctx) {
        currentPosition++;
        currentEntry = entries.get(currentPosition);
    }
}
