package df.base.internal.parser.parser;

import df.base.internal.parser.configurer.Configurator;

public class ParserContextConfigurator implements Configurator<ParserContext> {

    @Override
    public void configure(ParserContext object) {
        object.add(new IdentifierParser());
        object.add(new LiteralParser());
    }

}
