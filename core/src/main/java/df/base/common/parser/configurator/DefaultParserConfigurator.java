package df.base.common.parser.configurator;

import df.base.common.libs.ast.configurer.Configurator;
import df.base.common.parser.parser.*;
import df.base.common.libs.ast.parser.ParserContext;

public class DefaultParserConfigurator implements Configurator<ParserContext> {

    @Override
    public void configure(ParserContext context) {
        context.add(new IdentifierParser());
        context.add(new LiteralParser());
        context.add(new AnnotationParser());
        context.add(new ParametersParser());
        context.add(new ArrayParser());
        context.add(new ClassNameParser());
        context.add(new ExternalVariableParser());
        context.add(new CommaSeparatedParser());
    }

}
