package df.base.jpa;

import df.base.internal.hibernate.generator.IdPrefixGenerator;
import df.base.internal.hibernate.generator.PrefixedId;
import df.base.internal.hibernate.generator.PrefixedTableSequenceGenerator;

import static java.util.Objects.requireNonNull;

public class NamedEntityIdGenerator implements IdPrefixGenerator {

    @Override
    public void configure(PrefixedTableSequenceGenerator.GeneratorContext context, Object entity) {
        ensureType(EntityNameAware.class, entity);

        EntityNameAware entityNameAware = (EntityNameAware) entity;

        context.sequenceName("%s_%s"
                .formatted(context.sequenceName(), requireNonNull(entityNameAware.getName()).toUpperCase()));
    }

    @Override
    public String generate(Object ordinalID, PrefixedId annotation, Object entity) {
        ensureType(EntityNameAware.class, entity);

        EntityNameAware entityNameAware = (EntityNameAware) entity;
        String          prefix          = annotation.prefixValue();
        String          separator       = annotation.prefixSeparator();
        String          id              = annotation.numberFormat().formatted(ordinalID);
        String          name            = requireNonNull(entityNameAware.getName()).toUpperCase();

        return prefix + separator + name + separator + id;
    }

}
