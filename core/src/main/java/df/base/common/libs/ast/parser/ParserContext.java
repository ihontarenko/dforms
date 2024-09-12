package df.base.common.libs.ast.parser;

import java.util.HashMap;
import java.util.Map;

public interface ParserContext {

    ParserContext CONTEXT = new Context();

    Parser getParser(Class<? extends Parser> type);

    void add(Parser parser);

    class Context implements ParserContext {

        private final Map<Class<? extends Parser>, Parser> parsers = new HashMap<>();

        @Override
        public void add(Parser parser) {
            parsers.put(parser.getClass(), parser);
        }

        @Override
        public Parser getParser(Class<? extends Parser> type) {
            if (!parsers.containsKey(type)) {
                throw new ParserException(String.format("NO '%s' FOUND", type.getSimpleName()));
            }

            return parsers.get(type);
        }

    }

}
