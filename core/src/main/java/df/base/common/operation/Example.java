package df.base.common.operation;

import df.base.PackageCoreRoot;
import df.base.common.matcher.reflection.ClassMatchers;
import df.base.common.matcher.reflection.TypeMatchers;
import df.base.common.operation.annotation.OperationCommand;
import df.base.common.operation.compiler.HandlerDefinitionCompiler;
import df.base.common.parser.ParameterParser;
import df.base.common.reflection.ClassFinder;

import java.util.ArrayList;
import java.util.List;

public class Example {

    public static void main(String[] args) {

        ParameterParser parser = new ParameterParser();

        List<String> definitions = new ArrayList<>();

        definitions.add("#validation:non_empty//(message='test!')");
        definitions.add("#validation:int_size//(max=100)");
        definitions.add("#math:sum//(a=2, b=2, r=#sum(2, 2))");
        definitions.add("#persistence:post_save//(action='LOAD', dataSource=null)");
        definitions.add("#highlight:is_true//(value=#item.startsWith('t'), min=10)");

        OperationManager operationManager = new OperationManager();

        parser.getEvaluationContext().addCompiler(new HandlerDefinitionCompiler());

        parser.getEvaluationContext().setVariable("item", "test");

        for (String definition : definitions) {
            String[] parts   = definition.split("//");
            String   handler = parts[0];
            String   params  = parts[1];

            OperationDefinition handlerDefinition  = (OperationDefinition) parser.parse(handler)
                    .evaluate(parser.getEvaluationContext());

            handlerDefinition.setParameters(params);

            System.out.println(handlerDefinition);
        }

        for (Class<?> annotatedClass : ClassFinder.findAnnotatedClasses(OperationCommand.class, PackageCoreRoot.class)) {
            System.out.println(annotatedClass);
            System.out.println(TypeMatchers.isSupertype(Operation.class).matches(annotatedClass));
        }

    }

}
