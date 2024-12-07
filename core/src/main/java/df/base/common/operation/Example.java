package df.base.common.operation;

import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.operation.compiler.HandlerDefinitionCompiler;
import df.base.common.parser.ParserService;

import java.util.ArrayList;
import java.util.List;

public class Example {

    public static void main(String[] args) {

        ParserService     parser  = new ParserService();
        EvaluationContext context = parser.getEvaluationContext();

        List<String> definitions = new ArrayList<>();

        definitions.add("#validation:non_empty//(message='test!')");
        definitions.add("#validation:int_size//(max=100)");
        definitions.add("#math:sum//(a=2, b=2, r=#sum(2, 2))");
        definitions.add("#persistence:post_save//(action='LOAD', dataSource=null)");
        definitions.add("#highlight:is_true//(value=#item.startsWith('t'), min=10)");
        definitions.add("#controller:action//(key_a='value_a')");
        definitions.add("#operation:action//(key='value')");

        context.addCompiler(new HandlerDefinitionCompiler());
        context.setVariable("item", "test");

        for (String definition : definitions) {
            String[] parts   = definition.split("//");
            String   handler = parts[0];
            String   params  = parts[1];

            OperationDefinition operationDefinition = (OperationDefinition) parser.parse(handler).evaluate(context);

            operationDefinition.setParameters(params);

            System.out.println(operationDefinition);
            System.out.println(operationDefinition.getOperation());
            System.out.println(operationDefinition.getCommand());


        }
    }

}
