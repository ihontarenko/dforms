package df.base.common.commans;

import df.base.common.commans.compiler.HandlerDefinitionCompiler;
import df.base.common.context.Context;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.parser.ParserService;

import java.util.Map;

final public class CommandRequestBuilder {

    private final String        definition;
    private final String        parameters;
    private final ParserService parser = new ParserService();

    public CommandRequestBuilder(String definition, String parameters) {
        this.definition = definition;
        this.parameters = parameters;
        this.parser.getEvaluationContext().addCompiler(new HandlerDefinitionCompiler());
    }

    public CommandRequest build(Context context) {
        Node              routeNode           = parser.parse(definition);
        CommandRoute      commandRoute        = (CommandRoute) routeNode.evaluate(parser.getEvaluationContext());
        Node              queryParametersNode = parser.parse(parameters);
        EvaluationContext evaluationContext   = createNewEvaluationContext(context);

        if (queryParametersNode.evaluate(evaluationContext) instanceof Map<?, ?> parsedParameters) {
            return CommandRequest.create(commandRoute, (Map<String, Object>) parsedParameters);
        }

        throw new NoCommandFoundException(
                "Failed to parse the provided parameters [%s]. Please review the syntax of the passed parameters."
                        .formatted(parameters));
    }

    private EvaluationContext createNewEvaluationContext(Context context) {
        EvaluationContext evaluationContext = new EvaluationContext();

        evaluationContext.copyFrom(context);
        evaluationContext.copyFrom(parser.getEvaluationContext());

        return evaluationContext;
    }

}
