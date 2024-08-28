package df.base.internal.parser.parser;

import df.base.internal.parser.token.Token;

import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Stream.of;

public class SyntaxErrorException extends Error {

    public SyntaxErrorException(Parser parser, Token.Entry entry, Token... expected) {
        this(String.format("Parser [%s] was expected: %s but got %s at position %d",
                parser.getClass().getSimpleName(), of(expected).map(Objects::toString).collect(Collectors.joining(", ")),
                entry.token(), entry.position()));
    }

    public SyntaxErrorException(String message) {
        super(message);
    }

}
