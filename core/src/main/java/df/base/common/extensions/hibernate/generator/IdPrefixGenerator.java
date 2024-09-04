package df.base.common.extensions.hibernate.generator;

import org.hibernate.id.IdentifierGenerationException;

public interface IdPrefixGenerator {

    default void ensureType(Class<?> expected, Object entity) {
        if (!expected.isAssignableFrom(entity.getClass())) {
            throw new IdentifierGenerationException("EXPECTED TYPE '%s' BUT '%s' PASSED"
                    .formatted(expected.getSimpleName(), entity.getClass().getSimpleName()));
        }
    }

    default void configure(PrefixedTableSequenceGenerator.GeneratorContext context, Object entity) {
    }

    default String generate(Object ordinalID, PrefixedId annotation, Object entity) {
        return annotation.prefixValue() + annotation.prefixSeparator() + annotation.numberFormat().formatted(ordinalID);
    }

    class Default implements IdPrefixGenerator {
    }

}
