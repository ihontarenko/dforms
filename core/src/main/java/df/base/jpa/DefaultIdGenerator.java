package df.base.jpa;

import df.base.common.hibernate5.generator.IdPrefixGenerator;
import df.base.common.hibernate5.generator.PrefixedId;

public class DefaultIdGenerator implements IdPrefixGenerator {

    @Override
    public String generate(Object ordinalID, PrefixedId annotation, Object entity) {
        String prefix    = annotation.prefixValue();
        String separator = annotation.prefixSeparator();
        String id        = annotation.numberFormat().formatted(ordinalID);
        String hash      = "%04x".formatted(entity.hashCode());

        return "%s%s%s%s%s".formatted(prefix, separator, hash, separator, id).toUpperCase();
    }

}
