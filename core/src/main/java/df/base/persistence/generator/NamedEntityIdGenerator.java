package df.base.persistence.generator;

import df.base.common.extensions.hibernate.generator.IdPrefixGenerator;
import df.base.common.extensions.hibernate.generator.PrefixedId;
import df.base.common.libs.jbm.StringUtils;
import df.base.persistence.entity.EntityNameAware;

import static df.base.common.libs.jbm.StringUtils.underscored;
import static df.base.common.support.SlugifyTransliterator.slugify;
import static java.util.Objects.requireNonNull;

public class NamedEntityIdGenerator implements IdPrefixGenerator {

    /*@Override
    public void configure(PrefixedTableSequenceGenerator.GeneratorContext context, Object entity) {
        ensureType(EntityNameAware.class, entity);

        EntityNameAware entityNameAware = (EntityNameAware) entity;

        context.sequenceName("%s_%s"
                .formatted(context.sequenceName(), requireNonNull(entityNameAware.getName()).toUpperCase()));
    }*/

    @Override
    public String generate(Object ordinalID, PrefixedId annotation, Object entity) {
        ensureType(EntityNameAware.class, entity);

        EntityNameAware entityNameAware = (EntityNameAware) entity;

        String prefix    = annotation.prefixValue();
        String separator = annotation.prefixSeparator();
        String id        = annotation.numberFormat().formatted(ordinalID);
        String name      = slugify(underscored(requireNonNull(entityNameAware.getName()))).toLowerCase();

        return (prefix + separator + name + separator + id).toLowerCase();
    }

}
