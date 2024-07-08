package df.parent.library.parser.parser;

import df.parent.library.parser.configurer.Configurator;

public class ParserContextConfigurator implements Configurator<ParserContext> {

    @Override
    public void configure(ParserContext object) {
        object.add(new IdentifierParser());
        object.add(new LiteralParser());
    }

}
