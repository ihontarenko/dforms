package df.base.html.parameter.configurator;

import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.parser.ParserContextConfigurator;
import df.base.html.parameter.parser.ScopeNameParser;

public class ParameterParserConfigurator extends ParserContextConfigurator {

    @Override
    public void configure(ParserContext context) {
        super.configure(context);

        context.add(new ScopeNameParser());
    }

}
