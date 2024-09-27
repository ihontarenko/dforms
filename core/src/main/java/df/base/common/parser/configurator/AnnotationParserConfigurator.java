package df.base.common.parser.configurator;

import df.base.common.parser.parser.ArrayParser;
import df.base.common.parser.parser.ClassNameParser;
import df.base.common.parser.parser.ParametersParser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.parser.ParserContextConfigurator;
import df.base.common.parser.parser.AnnotationParser;

public class AnnotationParserConfigurator extends ParserContextConfigurator {

    @Override
    public void configure(ParserContext context) {
        super.configure(context);
        context.add(new AnnotationParser());
        context.add(new ParametersParser());
        context.add(new ArrayParser());
        context.add(new ClassNameParser());
    }

}
