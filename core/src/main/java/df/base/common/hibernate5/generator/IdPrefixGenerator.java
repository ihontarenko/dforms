package df.base.common.hibernate5.generator;

import df.base.jpa.User;
import org.hibernate.id.IdentifierGenerationException;

public interface IdPrefixGenerator {

    default void ensureType(Class<?> expected, Object entity) {
        if (!expected.isAssignableFrom(entity.getClass())) {
            throw new IdentifierGenerationException("Expected type %s but %s passed"
                    .formatted(User.class.getName(), entity.getClass().getName()));
        }
    }

    default void configure(PrefixedTableSequenceGenerator.GeneratorContext context, Object entity) {
    }

    default String generated(Object ordinalID, PrefixedId annotation, Object entity) {
        return annotation.prefixValue() + annotation.prefixSeparator() + annotation.numberFormat().formatted(ordinalID);
    }

    class Default implements IdPrefixGenerator {
    }

}
