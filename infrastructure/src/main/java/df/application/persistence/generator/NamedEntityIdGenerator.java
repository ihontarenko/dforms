package df.application.persistence.generator;

import df.common.extensions.hibernate.generator.IdPrefixGenerator;
import df.common.extensions.hibernate.generator.PrefixedId;
import df.application.persistence.entity.EntityNameAware;

import static org.jmouse.util.Strings.underscored;
import static df.common.support.SlugifyTransliterator.slugify;
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
        String name      = getNameForEntity(entityNameAware).toLowerCase();

        return (prefix + separator + name + separator + id).toLowerCase();
    }

    private static String getNameForEntity(EntityNameAware nameAware) {
        String underscored = underscored(requireNonNull(nameAware.getName()));

        if (underscored.indexOf('.') != -1) {
            underscored = underscored.substring(underscored.lastIndexOf('.') + 1);
        }

        return slugify(underscored);
    }

}
