package df.base.common.libs.parser.parser;

import df.base.common.libs.parser.configurer.Configurator;

public class ParserContextConfigurator implements Configurator<ParserContext> {

    @Override
    public void configure(ParserContext object) {
        object.add(new IdentifierParser());
        object.add(new LiteralParser());
    }

}
