package df.base.validation;

import df.base.common.jbm.ReflectionUtils;
import df.base.common.support.JpaCriteria;
import df.base.common.support.JpaCriteriaMapper;
import df.base.common.support.JpaHelper;
import df.base.common.support.SpELEvaluator;
import df.base.validation.constraint.JpaResource;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.util.List;

public class JpaResourceValidator implements ConstraintValidator<JpaResource, Object> {

    private final JpaHelper     jpaHelper;
    private final SpELEvaluator evaluator;
    private       Class<?>      entityClass;
    private       Fields[]      fields;
    private       String        applier;
    private       String        target;

    public JpaResourceValidator(JpaHelper jpaHelper, SpELEvaluator evaluator) {
        this.jpaHelper = jpaHelper;
        this.evaluator = evaluator;
    }

    @Override
    public void initialize(JpaResource annotation) {
        this.applier = annotation.applier();
        this.fields = annotation.fields();
        this.entityClass = annotation.entityClass();
        this.target = annotation.target();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Object  idValue            = ReflectionUtils.getFieldValue(object, applier);
        boolean isValid            = true;
        boolean require = idValue == null;

        if (StringUtils.hasText(applier)) {
            SpelExpressionParser parser     = new SpelExpressionParser();
            Expression           expression = parser.parseExpression(applier);
            require = evaluator.evaluate(expression, Boolean.class);
        }

        if (require) {
            isValid = doValidate(object);
        }

        if (!isValid) {
            addConstraintViolation(context);
        }

        return isValid;
    }

    private boolean doValidate(Object object) {
        JpaCriteria[]      jpaCriteria = new JpaCriteriaMapper(evaluator, object).map(fields);
        TypedQuery<Object> typedQuery  = jpaHelper.createTypedQuery((Class<Object>) entityClass, jpaCriteria);
        List<Object>       resultSet   = typedQuery.getResultList();

        return resultSet.size() == 0;
    }

    private void addConstraintViolation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String                     messageTemplate  = context.getDefaultConstraintMessageTemplate();
        ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(messageTemplate);
        violationBuilder.addPropertyNode(target).addConstraintViolation();
    }

}