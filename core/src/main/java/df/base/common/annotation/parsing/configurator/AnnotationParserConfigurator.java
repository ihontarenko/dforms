package df.base.common.annotation.parsing.configurator;

import df.base.common.annotation.parsing.parser.AnnotationParameterParser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.parser.ParserContextConfigurator;
import df.base.common.annotation.parsing.parser.AnnotationParser;

public class AnnotationParserConfigurator extends ParserContextConfigurator {

    @Override
    public void configure(ParserContext context) {
        super.configure(context);

        context.add(new AnnotationParser());
        context.add(new AnnotationParameterParser());
    }

}
