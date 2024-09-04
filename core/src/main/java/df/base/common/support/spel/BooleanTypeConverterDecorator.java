package df.base.common.support.spel;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.TypeConverter;

import java.util.Collection;

public class BooleanTypeConverterDecorator implements TypeConverter {

    private static final TypeDescriptor BOOLEAN_TYPE = TypeDescriptor.valueOf(Boolean.class);
    private static final TypeDescriptor NUMBER_TYPE  = TypeDescriptor.valueOf(Number.class);

    private final TypeConverter decorated;

    public BooleanTypeConverterDecorator(TypeConverter decorated) {
        this.decorated = decorated;
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        boolean isApplicable = targetType.isAssignableTo(BOOLEAN_TYPE)
                && (sourceType.isAssignableTo(NUMBER_TYPE) || sourceType.isCollection() || sourceType.isArray());

        return isApplicable || decorated.canConvert(sourceType, targetType);
    }

    @Override
    public Object convertValue(Object value, TypeDescriptor sourceDescriptor, TypeDescriptor targetDescriptor) {
        if (targetDescriptor.isAssignableTo(BOOLEAN_TYPE)) {

            if (value instanceof Number) {
                return ((Number) value).intValue() != 0;
            }

            if (sourceDescriptor.isCollection()) {
                return ! ((Collection<?>) value).isEmpty();
            }

            if (sourceDescriptor.isArray()) {
                return ((Object[]) value).length != 0;
            }

            return value;

        } else {
            return decorated.convertValue(value, sourceDescriptor, targetDescriptor);
        }
    }

}
