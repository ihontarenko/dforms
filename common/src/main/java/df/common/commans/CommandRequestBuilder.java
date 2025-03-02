package df.common.commans;

import df.common.commans.compiler.HandlerDefinitionCompiler;
import df.common.provider.bean.SpringBeanProvider;
import org.jmouse.common.support.context.Context;
import org.jmouse.common.ast.compiler.EvaluationContext;
import org.jmouse.common.ast.node.Node;
import org.jmouse.expression.ParserService;

import java.util.Map;

/**
 * {@code CommandRequestBuilder} facilitates the creation of {@link CommandRequest} instances
 * by lexer command definitions and parameters into executable requests.
 *
 * <p>This class uses the {@link ParserService} to parse and evaluate the provided command definitions
 * and parameters, leveraging the {@link HandlerDefinitionCompiler} to handle custom command routing.</p>
 */
public final class CommandRequestBuilder {

    private final String        definition;
    private final String        parameters;
    private final ParserService parser = new ParserService();

    /**
     * Constructs a {@code CommandRequestBuilder} with the given command definition and parameters.
     *
     * @param definition the string representation of the command definition
     * @param parameters the string representation of the query parameters
     */
    public CommandRequestBuilder(String definition, String parameters) {
        this.definition = definition;
        this.parameters = parameters;
        this.parser.getEvaluationContext().addCompiler(new HandlerDefinitionCompiler());
    }

    /**
     * Builds a {@link CommandRequest} by lexer and evaluating the provided command definition
     * and parameters in the given context.
     *
     * <p>The method uses the {@link ParserService} to parse the command definition and parameters
     * into {@link Node} objects, evaluates them within a combined {@link EvaluationContext}, and
     * creates a {@link CommandRequest} if successful.</p>
     *
     * @param context the {@link Context} for evaluating the command and parameters
     * @return a {@link CommandRequest} representing the parsed and evaluated command and parameters
     * @throws RouteNotFoundException if lexer or evaluation of the parameters fails
     */
    public CommandRequest build(Context context) {
        Node              routeNode           = parser.parse(definition);
        CommandRoute      commandRoute        = (CommandRoute) routeNode.evaluate(parser.getEvaluationContext());
        Node              queryParametersNode = parser.parse(parameters);
        EvaluationContext evaluationContext   = createNewEvaluationContext(context);

        if (queryParametersNode.evaluate(evaluationContext) instanceof Map<?, ?> parsedParameters) {
            evaluationContext.setProperties((Map<Object, Object>) parsedParameters);
            return CommandRequest.create(commandRoute, (Map<String, Object>) parsedParameters, evaluationContext);
        }

        throw new RouteNotFoundException(
                "Failed to parse the provided parameters [%s]. Please review the syntax of the passed parameters."
                        .formatted(parameters));
    }

    /**
     * Creates a new {@link EvaluationContext} by merging the provided {@link Context}
     * with the evaluation context of the {@link ParserService}.
     *
     * @param context the {@link Context} to merge into the new evaluation context
     * @return a combined {@link EvaluationContext}
     */
    private EvaluationContext createNewEvaluationContext(Context context) {
        EvaluationContext evaluationContext = new EvaluationContext();

        evaluationContext.copyFrom(context);
        evaluationContext.copyFrom(parser.getEvaluationContext());
        evaluationContext.setBeanProvider(new SpringBeanProvider());

        return evaluationContext;
    }
}
