package df.base.common.support.jpa;

import df.base.common.Mapper;
import df.base.common.support.spel.SpelEvaluator;
import df.base.common.validation.jakarta.Fields;
import df.base.common.validation.jakarta.Fields.ValueType;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;

import static df.base.common.libs.jbm.ReflectionUtils.getFieldValue;

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
            case FIELD_NAME -> getFieldValue(object, rawValue);
        };
    }

}
