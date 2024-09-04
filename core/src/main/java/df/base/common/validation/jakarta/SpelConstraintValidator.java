package df.base.common.validation.jakarta;

import df.base.common.support.spel.SpelEvaluator;
import df.base.common.validation.jakarta.constraint.SpelConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import static java.util.Objects.requireNonNull;


/**
 * @link https://github.com/jirutka/validator-spring
 */
public class SpelConstraintValidator implements ConstraintValidator<SpelConstraint, Object> {

    private final SpelEvaluator spel;
    private       Expression    expression;
    private       Expression    applier;
    private       String        pointer;

    public SpelConstraintValidator(SpelEvaluator spel) {
        this.spel = spel;
    }

    @Override
    public void initialize(SpelConstraint constraint) {
        ExpressionParser parser = new SpelExpressionParser();

        pointer = constraint.pointer();
        expression = parser.parseExpression(constraint.value());

        if (StringUtils.hasText(constraint.applier())) {
            applier = parser.parseExpression(constraint.applier());
        }
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valid = true;

        if (object != null) {
            boolean applicable = true;

            // attach target object to evaluation context
            spel.initialize(ctx -> ctx.setRootObject(object));

            if (applier != null) {
                applicable = spel.evaluate(applier, Boolean.class);
            }

            if (applicable) {
                valid = spel.evaluate(expression, Boolean.class);
            }

            // detach target object to evaluation context
            spel.uninitialize(ctx -> ctx.setRootObject(null));
        }

        if (!valid) {
            addConstraintViolation(context);
        }

        return valid;
    }

    private void addConstraintViolation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String                                                messageTemplate  = context.getDefaultConstraintMessageTemplate();
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(messageTemplate);
        violationBuilder.addPropertyNode(requireNonNull(pointer)).addConstraintViolation();
    }

}
