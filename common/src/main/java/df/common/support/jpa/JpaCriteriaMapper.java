package df.common.support.jpa;

import org.jmouse.common.mapping.Mapper;
import df.common.support.spel.SpelEvaluator;
import df.common.validation.jakarta.Fields;
import df.common.validation.jakarta.Fields.ValueType;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.jmouse.core.reflection.Reflections;

import java.util.ArrayList;
import java.util.List;

public class JpaCriteriaMapper implements Mapper<Fields[], JpaCriteria[]> {

    private final SpelEvaluator evaluator;
    private final Object        object;

    public JpaCriteriaMapper(SpelEvaluator evaluator, Object object) {
        this.evaluator = evaluator;
        this.object = object;
    }

    @Override
    public JpaCriteria[] map(Fields[] source) {
        List<JpaCriteria> criteria = new ArrayList<>();

        evaluator.initialize(ctx -> ctx.setRootObject(object));

        for (Fields fields : source) {
            criteria.add(new JpaCriteria.SimpleCriteria(fields.entityField(),
                    resolveObjectValue(this.object, fields), fields.comparison()));
        }

        evaluator.uninitialize(ctx -> ctx.setRootObject(null));

        return criteria.toArray(JpaCriteria[]::new);
    }

    @Override
    public Fields[] reverse(JpaCriteria[] source) {
        throw new UnsupportedOperationException();
    }

    private Object resolveObjectValue(Object object, Fields fields) {
        ValueType type     = fields.objectValue().type();
        String    rawValue = fields.objectValue().value();

        return switch (type) {
            case SPEL_EXPRESSION -> {
                ExpressionParser parser = new SpelExpressionParser();
                yield evaluator.evaluate(parser.parseExpression(rawValue), Object.class);
            }
            case RAW_VALUE -> rawValue;
            case FIELD_NAME -> {
                Object objectValue = Reflections.getFieldValue(object, rawValue);

                // try to extract the value through the getter
                // when the real field-name is different from the structured getter name
                if (objectValue == null) {
                    objectValue = Reflections.getMethodValue(object, rawValue);
                }

                yield objectValue;
            }
        };
    }

}
