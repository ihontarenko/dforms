package df.base.common.parser.configurator;

import df.base.common.parser.parser.*;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.parser.ParserContextConfigurator;

public class DefaultParserConfigurator extends ParserContextConfigurator {

    @Override
    public void configure(ParserContext context) {
        super.configure(context);
        context.add(new AnnotationParser());
        context.add(new ParametersParser());
        context.add(new ArrayParser());
        context.add(new ClassNameParser());
        context.add(new ExternalVariableParser());
        context.add(new CommaSeparatedParser());
    }

}
