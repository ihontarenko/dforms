package df.base.common.libs.ast.parser;

import df.base.common.libs.ast.token.Token;

import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Stream.of;

public class SyntaxErrorException extends Error {

    public SyntaxErrorException(Parser parser, Token.Entry entry, Token... expected) {
        this("[%s] expected token %s, but encountered %s at position %d".formatted(
                parser.getClass().getSimpleName(),
                of(expected).map(Objects::toString).collect(Collectors.joining(", ")),
                entry.token(),
                entry.position())
        );
    }

    public SyntaxErrorException(String message) {
        super(message);
    }

}
